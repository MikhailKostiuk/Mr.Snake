package com.example.mrsnake;

import com.example.mrsnake.framework.Screen;
import com.example.mrsnake.framework.impl.AndroidGame;

public class MrSnakeGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
