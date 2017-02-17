package com.example.mrsnake.framework.impl;

import android.graphics.Bitmap;

import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Pixmap;


public class AndroidPixmap implements Pixmap {

    Bitmap mBitmap;
    Graphics.PixmapFormat mFormat;

    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat pixmapFormat) {
        this.mBitmap = bitmap;
        this.mFormat = pixmapFormat;
    }

    @Override
    public int getWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public Graphics.PixmapFormat getFormat() {
        return mFormat;
    }

    @Override
    public void dispose() {
        mBitmap.recycle();
    }
}
