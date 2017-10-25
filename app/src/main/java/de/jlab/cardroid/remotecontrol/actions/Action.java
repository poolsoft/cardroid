package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;

import java.util.HashMap;

public interface Action {

    void init(HashMap<String, String> propertyMap);
    boolean execute(Context context) throws ActionException;

}
