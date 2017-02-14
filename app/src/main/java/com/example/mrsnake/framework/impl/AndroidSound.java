package com.example.mrsnake.framework.impl;

import android.media.SoundPool;

import com.example.mrsnake.framework.Sound;


public class AndroidSound implements Sound {

    SoundPool mSoundPool;
    int mSoundId;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.mSoundPool = soundPool;
        this.mSoundId = soundId;
    }

    @Override
    public void play(float volume) {
        mSoundPool.play(mSoundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        mSoundPool.unload(mSoundId);
    }
}
