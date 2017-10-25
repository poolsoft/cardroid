package de.jlab.cardroid.remotecontrol.actions;

import android.util.AndroidException;

public class ActionException extends AndroidException {

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String name, Throwable cause) {
        super(name, cause);
    }

}
