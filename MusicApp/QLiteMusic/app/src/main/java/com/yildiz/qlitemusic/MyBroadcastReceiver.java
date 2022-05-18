package com.yildiz.qlitemusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int state = intent.getIntExtra("state", 0);
        if(state == 1){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
        }
        else if(state == 2){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
        }
        else if(state == 3){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
        }
        else if(state == 4){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, 0);
        }
    }
}