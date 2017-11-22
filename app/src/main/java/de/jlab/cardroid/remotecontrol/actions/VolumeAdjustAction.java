package de.jlab.cardroid.remotecontrol.actions;

import android.content.Context;
import android.media.AudioManager;

import java.util.HashMap;

public class VolumeAdjustAction implements Action {
    private int stream = AudioManager.USE_DEFAULT_STREAM_TYPE;
    private int direction;

    @Override
    public void init(HashMap<String, String> propertyMap) {
        /*
            AudioSystem.STREAM_DEFAULT = -1;
            AudioSystem.STREAM_VOICE_CALL = 0;
            AudioSystem.STREAM_SYSTEM = 1;
            AudioSystem.STREAM_RING = 2;
            AudioSystem.STREAM_MUSIC = 3;
            AudioSystem.STREAM_ALARM = 4;
            AudioSystem.STREAM_NOTIFICATION = 5;
            AudioSystem.STREAM_BLUETOOTH_SCO = 6;
            AudioSystem.STREAM_SYSTEM_ENFORCED = 7;
            AudioSystem.STREAM_DTMF = 8;
            AudioSystem.STREAM_TTS = 9;
            AudioManager.USE_DEFAULT_STREAM_TYPE = Integer.MIN_VALUE
         */
        this.stream = Integer.parseInt(propertyMap.get("volume_stream"));
        /*
            AudioManager.ADJUST_LOWER
            AudioManager.ADJUST_RAISE
         */
        this.direction = Integer.parseInt(propertyMap.get("volume_direction"));
    }

    @Override
    public boolean execute(Context context) throws ActionException {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustSuggestedStreamVolume(this.stream, direction, AudioManager.FLAG_SHOW_UI);

        return true;
    }

    public ActionSettings createActionSettings(Context context) {
        return null;
    }
}
