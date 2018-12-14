package com.chunbin.app.controller.video;

import android.content.Context;
import android.media.AudioManager;

public class AudioController {
    public static void turnDown(Context context, float yDilta, int heightPixels) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        //当前音量
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //获取最大英两
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算音量的改变量
        float streamDelta = yDilta / heightPixels * streamMaxVolume;

        //改变后的音量
        int changeStreamVolume = (int) Math.max(0, streamVolume - streamDelta);

        //将改变后的音量设置给西屯
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,changeStreamVolume,AudioManager.FLAG_SHOW_UI);
    }

    public static void turnup(Context context, float yDilta, int heightPixels) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        //当前音量
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //获取最大英两
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算音量的改变量
        float streamDelta = yDilta / heightPixels * streamMaxVolume;

        //改变后的音量
        int changeStreamVolume = (int) Math.min(streamMaxVolume, streamVolume - streamDelta);

        //将改变后的音量设置给西屯
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,changeStreamVolume,AudioManager.FLAG_SHOW_UI);
    }
}
