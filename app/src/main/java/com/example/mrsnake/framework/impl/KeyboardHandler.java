package com.example.mrsnake.framework.impl;

import android.view.KeyEvent;
import android.view.View;

import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Pool;

import java.util.ArrayList;
import java.util.List;


public class KeyboardHandler implements View.OnKeyListener {

    boolean[] mPressedKeys = new boolean[128];
    Pool<Input.KeyEvent> mKeyEventPool;
    List<Input.KeyEvent> mKeyEventsBuffer;
    List<Input.KeyEvent> mKeyEvents = new ArrayList<>();

    public KeyboardHandler(View view) {
        Pool.PoolObjectFactory<Input.KeyEvent> factory = new Pool.PoolObjectFactory<Input.KeyEvent>() {

            @Override
            public Input.KeyEvent createObject() {
                return new Input.KeyEvent();
            }
        };

        mKeyEventPool = new Pool<>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return mPressedKeys[keyCode];
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized (this) {
            Input.KeyEvent keyEvent = mKeyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                keyEvent.type = Input.KeyEvent.KEY_DOWN;
                if (keyCode > 0 && keyCode < 127) {
                    mPressedKeys[keyCode] = true;
                }
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                keyEvent.type = Input.KeyEvent.KEY_UP;
                if (keyCode > 0 && keyCode < 127) {
                    mPressedKeys[keyCode] = false;
                }
            }

            mKeyEventsBuffer.add(keyEvent);
        }
        return false;
    }

    public List<Input.KeyEvent> getKeyEvents() {

        synchronized (this) {
            int length = mKeyEvents.size();
            for (int i = 0; i < length; i++) {
                mKeyEventPool.free(mKeyEvents.get(i));
            }
            mKeyEvents.clear();
            mKeyEvents.addAll(mKeyEventsBuffer);
            mKeyEventsBuffer.clear();
        }
        return mKeyEvents;
    }
}
