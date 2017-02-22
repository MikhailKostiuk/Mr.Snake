package com.example.mrsnake;


public class Fruit {

    public static final int APPLE = 0;
    public static final int PINEAPPLE = 1;
    public static final int GRAPES = 2;

    public int x, y;
    public int type;

    public Fruit(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
