package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.HashMap;

public class VoiceAction implements Action {
    @Override
    public void init(HashMap<String, String> propertyMap) {
        // intentionally left blank
    }

    @Override
    public boolean execute(Context context) throws ActionException {
        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);
        context.startActivity(voiceIntent);
        return true;
    }
}
