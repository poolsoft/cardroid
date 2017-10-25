package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import java.util.HashMap;

public class MediaEventAction implements Action {
    private int keyCode = -1;

    @Override
    public void init(HashMap<String, String> propertyMap) {
        /*
            KeyEvent.KEYCODE_MEDIA_PREVIOUS
            KeyEvent.KEYCODE_MEDIA_NEXT
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
         */
        this.keyCode = Integer.parseInt(propertyMap.get("media_keycode"));
    }

    @Override
    public boolean execute(Context context) throws ActionException {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, this.keyCode);
        audioManager.dispatchMediaKeyEvent(downEvent);

        KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, this.keyCode);
        audioManager.dispatchMediaKeyEvent(upEvent);

        return true;
    }

    @Override
    public Fragment createActionSettingsFragment(Context context) {
        return null;
    }
}
