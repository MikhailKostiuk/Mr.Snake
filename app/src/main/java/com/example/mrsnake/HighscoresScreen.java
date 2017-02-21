package com.example.mrsnake;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Screen;

import java.util.List;


public class HighscoresScreen extends Screen {

    String[] mLines = new String[5];

    public HighscoresScreen(Game game) {
        super(game);

        for (int i = 0; i < 5; i++) {
            mLines[i] = "" + (i + 1) + ". " + Settings.sHighscores[i];
        }
    }

    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = mGame.getInput().getTouchEvents();
        mGame.getInput().getKeyEvents();

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                if (touchEvent.x < 300 && touchEvent.y > 1620) {
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    mGame.setScreen(new MainMenuScreen(mGame));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.mainMenu, 20, 110, 0, 220, 1040, 245);

        int y = 400;
        for (int i = 0; i < 5; i++) {
            drawText(graphics, mLines[i], 260, y);
            y += 50;
        }

        graphics.drawPixmap(Assets.buttons, 0, 1620, 300, 300, 300, 300);
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

    private void drawText(Graphics graphics, String line, int x, int y) {
        int length = line.length();
        for (int i = 0; i < length  ; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 130;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 1400;
                srcWidth = 65;
            } else {
                srcX = (character - '0') * 140; // 140 is single number width
                srcWidth = 140;
            }

            graphics.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 213);
            x += srcWidth;
        }
    }
}
