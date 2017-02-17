package com.example.mrsnake.framework;


public interface Pixmap {

    int getWidth();

    int getHeight();

    Graphics.PixmapFormat getFormat();

    void dispose();
}
