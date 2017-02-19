package com.example.mrsnake.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import com.example.mrsnake.framework.Audio;
import com.example.mrsnake.framework.FileIO;
import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Screen;


public abstract class AndroidGame extends Activity implements Game {

    AndroidFastRenderView mAndroidFastRenderView;
    Graphics mGraphics;
    Audio mAudio;
    Input mInput;
    FileIO mFileIO;
    Screen mScreen;
    PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 1920 : 1080;
        int frameBufferHeight = isLandscape ? 1080 : 1920;
        Bitmap frameBuffer =
                Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        float scaleX =
                (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY =
                (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        mAndroidFastRenderView =new AndroidFastRenderView(this, frameBuffer);
        mGraphics = new AndroidGraphics(getAssets(), frameBuffer);
        mFileIO = new AndroidFileIO(this);
        mAudio = new AndroidAudio(this);
        mInput = new AndroidInput(this, mAndroidFastRenderView, scaleX, scaleY);
        mScreen = getStartScreen();

        setContentView(mAndroidFastRenderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWakeLock.acquire();
        mScreen.resume();
        mAndroidFastRenderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mWakeLock.release();
        mAndroidFastRenderView.pause();
        mScreen.pause();

        if (isFinishing()) {
            mScreen.dispose();
        }
    }

    @Override
    public Input getInput() {
        return mInput;
    }

    @Override
    public FileIO getFileIO() {
        return mFileIO;
    }

    @Override
    public Graphics getGraphics() {
        return mGraphics;
    }

    @Override
    public Audio getAudio() {
        return mAudio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.mScreen.pause();
        this.mScreen.dispose();
        screen.pause();
        screen.update(0);
        this.mScreen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return mScreen;
    }

    @Override
    public Screen getStartScreen() {
        return null;
    }
}
