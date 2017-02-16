package com.example.mrsnake.framework.impl;


import android.view.MotionEvent;
import android.view.View;

import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Pool;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler {

    private static final int MAX_TOUCHPOINTS = 10;

    boolean[] mIsTouched = new boolean[MAX_TOUCHPOINTS];
    int[] mTouchX = new int[MAX_TOUCHPOINTS];
    int[] mTouchY = new int[MAX_TOUCHPOINTS];
    int[] mId = new int[MAX_TOUCHPOINTS];
    Pool<Input.TouchEvent> mTouchEventPool;
    List<Input.TouchEvent> mTouchEvents = new ArrayList<>();
    List<Input.TouchEvent> mTouchEventsBuffer = new ArrayList<>();
    float mScaleX;
    float mScaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        mTouchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);

        mScaleX = scaleX;
        mScaleY = scaleY;
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return false;
            } else {
                return mIsTouched[index];
            }
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return mTouchX[index];
            }
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return mTouchY[index];
            }
        }
    }

    @Override
    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int length = mTouchEvents.size();
            for (int i = 0; i < length; i++) {
                mTouchEventPool.free(mTouchEvents.get(i));
            }
            mTouchEvents.clear();
            mTouchEvents.addAll(mTouchEventsBuffer);
            mTouchEventsBuffer.clear();
            return mTouchEvents;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerCount = event.getPointerCount();
            Input.TouchEvent touchEvent;

            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    mIsTouched[i] = false;
                    mId[i] = -1;
                }
                int pointerId = event.getPointerId(pointerIndex);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should
                    // process it for this touch point
                    continue;
                }
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = mTouchEventPool.newObject();
                        touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = mTouchX[i] = (int) (event.getX(i) * mScaleX);
                        touchEvent.y = mTouchY[i] = (int) (event.getY(i) * mScaleY);
                        mIsTouched[i] = true;
                        mId[i] = pointerId;
                        mTouchEventsBuffer.add(touchEvent);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = mTouchEventPool.newObject();
                        touchEvent.type = Input.TouchEvent.TOUCH_UP;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = mTouchX[i] = (int) (event.getX(i) * mScaleX);
                        touchEvent.y = mTouchY[i] = (int) (event.getY(i) * mScaleY);
                        mIsTouched[i] = false;
                        mId[i] = -1;
                        mTouchEventsBuffer.add(touchEvent);
                        break;
                }
            }
            return true;
        }
    }

    // returns the index for a given pointerId or −1 if no index.
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (mId[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
