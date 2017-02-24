package com.example.mrsnake;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Screen;
import com.example.mrsnake.framework.Graphics.PixmapFormat;


public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        Graphics graphics = mGame.getGraphics();

        Assets.background = graphics.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = graphics.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = graphics.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = graphics.newPixmap("buttons.png", PixmapFormat.ARGB4444);
//        Assets.help1 = graphics.newPixmap("help1.png", PixmapFormat.ARGB4444);
//        Assets.help2 = graphics.newPixmap("help2.png", PixmapFormat.ARGB4444);
//        Assets.help3 = graphics.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = graphics.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.pause = graphics.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = graphics.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.headUp = graphics.newPixmap("headup.png", PixmapFormat.ARGB4444);
        Assets.headLeft = graphics.newPixmap("headleft.png", PixmapFormat.ARGB4444);
        Assets.headDown = graphics.newPixmap("headdown.png", PixmapFormat.ARGB4444);
        Assets.headRight = graphics.newPixmap("headright.png", PixmapFormat.ARGB4444);
        Assets.tail = graphics.newPixmap("tail.png", PixmapFormat.ARGB4444);
        Assets.apple = graphics.newPixmap("apple.png", PixmapFormat.ARGB4444);
        Assets.mango = graphics.newPixmap("mango.png", PixmapFormat.ARGB4444);
        Assets.grapes = graphics.newPixmap("grapes.png", PixmapFormat.ARGB4444);

        Assets.click = mGame.getAudio().newSound("click.ogg");
        Assets.eat = mGame.getAudio().newSound("eat.ogg");
        Assets.bitten = mGame.getAudio().newSound("bitten.ogg");

        Settings.load(mGame.getFileIO());

        mGame.setScreen(new MainMenuScreen(mGame));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
