package com.example.mrsnake;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Screen;

import java.util.List;


public class GameScreen extends Screen {

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState mGameState = GameState.Ready;
    World mWorld;
    int mOldScore = 0;
    String mScore = "0";

    public GameScreen(Game game) {
        super(game);
        mWorld = new World();
    }

    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = mGame.getInput().getTouchEvents();
        mGame.getInput().getKeyEvents();

        if (mGameState == GameState.Ready) {
            updateReady(touchEvents);
        }
        if (mGameState == GameState.Running) {
            updateRunning(touchEvents, deltaTime);
        }
        if (mGameState == GameState.Paused) {
            updatePaused(touchEvents);
        }
        if (mGameState == GameState.GameOver) {
            updateGameOver(touchEvents);
        }
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

    private void updateReady(List<Input.TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            mGameState = GameState.Running;
        }
    }

    private void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        int length = touchEvents.size();

        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);

            if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                if (touchEvent.y < 1620) {
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    mGameState = GameState.Paused;
                    return;
                }
            }

            if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN) {
                if (touchEvent.x < 300 && touchEvent.y > 1620) {
                    mWorld.snake.turnLeft();
                }
                if (touchEvent.x > 780 && touchEvent.y > 1620) {
                    mWorld.snake.turnRight();
                }
            }
        }
        mWorld.update(deltaTime);
        if (mWorld.gameOver) {
            if (Settings.sSoundEnabled) {
                Assets.bitten.play(1);
            }
            mGameState = GameState.GameOver;
        }
        if (mOldScore != mWorld.score) {
            mOldScore = mWorld.score;
            mScore = "" + mOldScore;
            if (Settings.sSoundEnabled) {
                Assets.eat.play(1);
            }
        }
    }

    private void updatePaused(List<Input.TouchEvent> touchEvents) {
        int length = touchEvents.size();

        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {

                // "RESUME"
                if (touchEvent.x > 205 && touchEvent. x < 875 &&
                        touchEvent.y > 760 && touchEvent.y < 961) {
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    mGameState = GameState.Running
                }

                // "QUIT"
                if (touchEvent.x > 355 && touchEvent.x < 725 &&
                        touchEvent.y > 1000 && touchEvent.y < 1216) {
                    if (Settings.sSoundEnabled) {
                        Assets.click.play(1);
                    }
                    mGame.setScreen(new MainMenuScreen(mGame));
                    return;
                }
            }
        }
    }

    private void updateGameOver(List <Input.TouchEvent> touchEvents) {
        int length = touchEvents.size();

        for(int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == Input.TouchEvent.TOUCH_UP) {

                if(touchEvent.x >= 780 && touchEvent.y >= 1620) {
                    if(Settings.sSoundEnabled)
                        Assets.click.play(1);
                    mGame.setScreen(new MainMenuScreen(mGame));
                    return;
                }
            }
        }
    }
}
