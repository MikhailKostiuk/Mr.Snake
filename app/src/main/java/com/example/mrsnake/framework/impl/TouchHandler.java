package com.example.mrsnake.framework.impl;

import android.view.View;

import com.example.mrsnake.framework.Input;

import java.util.List;


public interface TouchHandler extends View.OnTouchListener {

    boolean isTouchDown(int pointer);

    int getTouchX(int pointer);

    int getTouchY(int pointer);

    List<Input.TouchEvent> getTouchEvents();
}
