package com.example.mrsnake.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class AndroidFastRenderView extends SurfaceView implements Runnable {

    AndroidGame mGame;
    Bitmap mFrameBuffer;
    Thread mRenderThread = null;
    SurfaceHolder mSurfaceHolder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer) {
        super(game);
        this.mGame = game;
        this.mFrameBuffer = frameBuffer;
        this.mSurfaceHolder = getHolder();
    }

    public void resume() {
        running = true;
        mRenderThread = new Thread(this);
        mRenderThread.start();
    }

    public void pause() {
        running = false;
        while(true) {
            try {
                mRenderThread.join();
                return;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }

    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();

        while (running) {
            if (!mSurfaceHolder.getSurface().isValid()) {
                continue;
            }

            float deltaTime = (System.nanoTime() - startTime) / 1_000_000_000.0f;
            startTime = System.nanoTime();

            mGame.getCurrentScreen().update(deltaTime);
            mGame.getCurrentScreen().present(deltaTime);

            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(mFrameBuffer, null, dstRect, null);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
