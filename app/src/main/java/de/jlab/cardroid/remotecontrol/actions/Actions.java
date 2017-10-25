package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;

import java.util.HashMap;

import de.jlab.cardroid.R;

public enum Actions {
    MEDIA_EVENT("media_event", MediaEventAction.class),
    START_APP("start_app", StartAppAction.class),
    VOICE("voice", VoiceAction.class),
    VOLUME_ADJUST("volume_adjust", VolumeAdjustAction.class);


    private String identifier;
    private Class<? extends Action> actionClass;

    Actions(String identifier, Class<? extends Action> actionClass) {
        this.identifier     = identifier;
        this.actionClass    = actionClass;
    }

    public static Action createAction(String identifier, HashMap<String, String> properties, Context context) throws ActionException {
        for (Actions actionType : Actions.values()) {
            if (actionType.identifier.equals(identifier)) {
                try {
                    Action action = actionType.actionClass.newInstance();
                    action.init(properties);
                    return action;
                }
                catch (IllegalAccessException e) {
                    throw new ActionException("Cannot access constructor of packet " + actionType.actionClass.getSimpleName(), e);
                }
                catch (InstantiationException e) {
                    throw new ActionException("Cannot create instance of packet " + actionType.actionClass.getSimpleName(), e);
                }
            }
        }

        throw new ActionException(context.getString(R.string.action_exception_action_not_found, identifier));
    }
}
