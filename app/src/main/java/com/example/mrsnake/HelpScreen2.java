package com.example.mrsnake;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Screen;

import java.util.List;


public class HelpScreen2 extends Screen {

    public HelpScreen2(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = mGame.getInput().getTouchEvents();
        mGame.getInput().getKeyEvents();

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                if (touchEvent.x > 780 && touchEvent.y > 1620) {
                    mGame.setScreen(new HelpScreen3(mGame));
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }
                if (touchEvent.x < 300 && touchEvent.y > 1620) {
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
        graphics.drawPixmap(Assets.help2, 0, 0);
        graphics.drawPixmap(Assets.buttons, 780, 1620, 0, 300, 300, 300);
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
}
