package de.jlab.cardroid.remotecontrol;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import de.jlab.cardroid.car.CarSystem;
import de.jlab.cardroid.car.RemoteControl;
import de.jlab.cardroid.remotecontrol.actions.Action;
import de.jlab.cardroid.remotecontrol.actions.ActionException;
import de.jlab.cardroid.remotecontrol.actions.Actions;
import de.jlab.cardroid.storage.CarDroidDataOpenHelper;

public class RemoteControlListener implements CarSystem.ChangeListener<RemoteControl> {

    private static final String LOG_TAG = "RemoteControlListener";

    private Context context;

    public RemoteControlListener(Context context) {
        this.context = context;
    }

    @Override
    public void onChange(RemoteControl system) {
        int remoteControlId = system.getControlId();
        int buttonId = system.getButtonId();

        CarDroidDataOpenHelper dataHelper = new CarDroidDataOpenHelper(this.context);
        if (!dataHelper.remoteControlExists(remoteControlId)) {
            dataHelper.createOrUpdateRemoteControl(null, remoteControlId);
        }
        if (!dataHelper.remoteButtonExists(buttonId, remoteControlId)) {
            dataHelper.createOrUpdateRemoteButton(null, null, buttonId, remoteControlId);
        }

        String actionIdentifier = dataHelper.getButtonActionIdentifer(buttonId, remoteControlId);
        if (actionIdentifier == null) {
            // TODO show action dialog
        }
        HashMap<String, String> propertyMap = dataHelper.getButtonActionProperties(buttonId, remoteControlId);
        try {
            Action action = Actions.createAction(actionIdentifier, propertyMap, this.context);
            action.execute(this.context);
        }
        catch (ActionException e) {
            // TODO SHOW ERROR MESSAGE
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
}
