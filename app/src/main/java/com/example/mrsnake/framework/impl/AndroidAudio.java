package com.example.mrsnake.framework.impl;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.mrsnake.framework.Audio;
import com.example.mrsnake.framework.Music;
import com.example.mrsnake.framework.Sound;

import java.io.IOException;


public class AndroidAudio implements Audio {

    AssetManager mAssetManager;
    SoundPool mSoundPool;

    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.mAssetManager = activity.getAssets();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            this.mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(20)
                    .build();
        } else {
            this.mSoundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        }
    }

    @Override
    public Music newMusic(String fileName) {
        try {
            AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(fileName);
            return new AndroidMusic(assetFileDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + fileName + "'");
        }
    }

    @Override
    public Sound newSound(String fileName) {
        try {
            AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(fileName);
            int soundId = mSoundPool.load(assetFileDescriptor, 0);
            return new AndroidSound(mSoundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + fileName + "'");
        }
    }
}
