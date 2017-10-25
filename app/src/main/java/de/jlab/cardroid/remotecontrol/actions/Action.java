package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;
import android.view.View;

import java.util.HashMap;

public interface Action {

    void init(HashMap<String, String> propertyMap);
    boolean execute(Context context) throws ActionException;
    ActionSettings createActionSettings(Context context);

    interface ActionSettings {
        View onCreate(Context context);
        void onSave(HashMap<String, String> propertyMap);
    }

}
