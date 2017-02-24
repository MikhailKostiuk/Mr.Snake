package com.example.mrsnake;

import android.graphics.Color;

import com.example.mrsnake.framework.Game;
import com.example.mrsnake.framework.Graphics;
import com.example.mrsnake.framework.Input;
import com.example.mrsnake.framework.Pixmap;
import com.example.mrsnake.framework.Screen;

import java.util.List;


public class GameScreen extends Screen {

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState mGameState = GameState.Paused;
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
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.background, 0, 0);
        drawWorld(mWorld);

        if (mGameState == GameState.Running) {
            drawRunningUI();
        }
        if (mGameState == GameState.Paused) {
            drawPausedUI();
        }
        if (mGameState == GameState.GameOver) {
            drawGameOverUI();
        }

        drawText(graphics, mScore, graphics.getWidth() / 2 - mScore.length() * 140 / 2, 1663);
    }

    @Override
    public void pause() {
        if (mGameState == GameState.Running) {
            mGameState = GameState.Paused;
        }
        if (mWorld.gameOver) {
            Settings.addScore(mWorld.score);
            Settings.save(mGame.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        int length = touchEvents.size();

        for (int i = 0; i < length; i++) {
            Input.TouchEvent touchEvent = touchEvents.get(i);

            if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN) {
                if (touchEvent.x > 40 && touchEvent.x < 200 &&
                        touchEvent.y > 40 && touchEvent.y < 220) {
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
                    mGameState = GameState.Running;
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

                if(touchEvent.x >= 390 && touchEvent.x < 690 &&
                        touchEvent.y >= 1025 && touchEvent.y < 1325) {
                    if(Settings.sSoundEnabled)
                        Assets.click.play(1);
                    mGame.setScreen(new MainMenuScreen(mGame));
                    return;
                }
            }
        }
    }

    private void drawWorld(World world) {
        Graphics graphics = mGame.getGraphics();
        Snake snake = world.snake;
        SnakePart head = snake.snakeParts.get(0);
        Fruit fruit = world.fruit;

        Pixmap fruitPixmap = null;
        switch (fruit.type) {
            case Fruit.APPLE:
                fruitPixmap = Assets.apple;
                break;
            case Fruit.MANGO:
                fruitPixmap = Assets.mango;
                break;
            case Fruit.GRAPES:
                fruitPixmap = Assets.grapes;
                break;
        }
        int x = fruit.x * 90;
        int y = fruit.y * 90;
        graphics.drawPixmap(fruitPixmap, x, y);

        int snakeLength = snake.snakeParts.size();
        for (int i = 1; i < snakeLength; i++) {
            SnakePart snakePart = snake.snakeParts.get(i);
            x = snakePart.x * 90;
            y = snakePart.y * 90;
            graphics.drawPixmap(Assets.tail, x, y);
        }

        Pixmap headPixmap = null;

        if(snake.direction == Snake.UP) {
            headPixmap = Assets.headUp;
            x = head.x * 90 - 1;
            y = head.y * 90 - 22;
        }
        if(snake.direction == Snake.LEFT) {
            headPixmap = Assets.headLeft;
            x = head.x * 90 - 22;
            y = head.y * 90 - 24;
        }
        if(snake.direction == Snake.DOWN) {
            headPixmap = Assets.headDown;
            x = head.x * 90 - 23;
            y = head.y * 90 - 2;
        }
        if(snake.direction == Snake.RIGHT) {
            headPixmap = Assets.headRight;
            x = head.x * 90 - 2;
            y = head.y * 90 - 24;
        }

        graphics.drawPixmap(
                headPixmap, x, y);
    }

    private void drawRunningUI() {
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.buttons, 40, 40, 370, 660, 160, 180);
        graphics.drawPixmap(Assets.buttons, 0, 1620, 300, 300, 300, 300);
        graphics.drawPixmap(Assets.buttons, 780, 1620, 0, 300, 300, 300);
        graphics.drawRect(0, 1619, 1080, 5, Color.BLACK);
    }

    private void drawPausedUI() {
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.pause, 206, 760);
        graphics.drawRect(0, 1619, 1080, 5, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics graphics = mGame.getGraphics();

        graphics.drawPixmap(Assets.gameOver, 49, 760);
        graphics.drawPixmap(Assets.buttons, 390, 1025, 0, 600, 300, 300);
        graphics.drawRect(0, 1619, 1080, 5, Color.BLACK);
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
