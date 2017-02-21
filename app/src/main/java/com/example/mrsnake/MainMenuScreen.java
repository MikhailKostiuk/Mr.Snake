package com.example.mrsnake;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Screen;

import java.util.List;


public class MainMenuScreen extends Screen {

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        Graphics graphics = mGame.getGraphics();
        List<Input.TouchEvent> touchEvents = mGame.getInput().getTouchEvents();
        mGame.getInput().getKeyEvents();

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {

                if (inBounds(touchEvent, 0, graphics.getHeight() - 300, 300, 300)) {
                    Settings.sSoundEnabled = !Settings.sSoundEnabled;
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                }

                if (inBounds(touchEvent, 340, 760, 400, 200)) {
                    mGame.setScreen(new GameScreen(mGame));
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }

                if (inBounds(touchEvent, 20, 999, 1040, 200)) {
                    mGame.setScreen(new HighscoresScreen(mGame));
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }

                if (inBounds(touchEvent, 340, 1039, 400, 200)) {
                    mGame.setScreen(new HelpScreen(mGame));
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.logo, 106, 105);
        graphics.drawPixmap(Assets.mainMenu, 20, 760);
        if (Settings.sSoundEnabled) {
            graphics.drawPixmap(Assets.buttons, 0, 1620, 0, 0, 300, 300);
        } else {
            graphics.drawPixmap(Assets.buttons, 0, 1620, 300, 0, 300, 300);
        }
    }

    @Override
    public void pause() {
        Settings.save(mGame.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private boolean inBounds(Input.TouchEvent touchEvent, int x, int y, int width, int height) {
        if (touchEvent.x > x && touchEvent.x < x + width - 1
                && touchEvent.y > y && touchEvent.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }
}
