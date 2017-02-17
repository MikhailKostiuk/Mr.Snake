package com.example.mrsnake.framework.impl;

import android.content.Context;
import android.view.View;

import com.example.mrsnake.framework.Input;

import java.util.List;


public class AndroidInput implements Input {

    KeyboardHandler mKeyboardHandler;
    TouchHandler mTouchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        mKeyboardHandler = new KeyboardHandler(view);
        mTouchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return mKeyboardHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return mTouchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return mTouchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return mTouchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return 0;
    }

    @Override
    public float getAccelY() {
        return 0;
    }

    @Override
    public float getAccelZ() {
        return 0;
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return mKeyboardHandler.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return mTouchHandler.getTouchEvents();
    }
}
