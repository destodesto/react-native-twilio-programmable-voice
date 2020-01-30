package com.hoxfon.react.RNTwilioVoice;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.content.SharedPreferences;
import android.net.Uri;

public class SoundPoolManager {

    public static final String PREFERENCES_KEY_NOTIFICATION_SETTINGS = "group.com.sosearch.sos";
    private Context context;
    private boolean playing = false;
    private static SoundPoolManager instance;
    private Ringtone ringtone = null;
    private final SharedPreferences notificationSettingsDefaultPersistence;


    private SoundPoolManager(Context context) {
        
        this.context = context;
        this.notificationSettingsDefaultPersistence = context.getSharedPreferences(PREFERENCES_KEY_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE);
    }

    public static SoundPoolManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundPoolManager(context);
        }
        return instance;
    }

    public void playRinging() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        String soundName = notificationSettingsDefaultPersistence.getString("notifPhoneRingSound", "iphone.mp3");
        int resId;
        
        if (!soundName.equals("iphone.mp3")) {
            // String soundName = bundle.getString("soundName");
            if (context.getResources().getIdentifier(soundName, "raw", context.getPackageName()) != 0) {
                resId = context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
            } else {
                soundName = soundName.substring(0, soundName.lastIndexOf('.'));
                resId = context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
            }

            soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
        }

        ringtone = RingtoneManager.getRingtone(context, soundUri);

        if (!playing) {
            ringtone.play();
            playing = true;
        }
    }

    public void stopRinging() {
        if (playing) {
            ringtone.stop();
            playing = false;
        }
    }

    public void playDisconnect() {
        if (!playing) {
            ringtone.stop();
            playing = false;
        }
    }

}
