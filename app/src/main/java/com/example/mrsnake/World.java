package com.example.mrsnake;


import java.util.Random;

public class World {

    final static int WORLD_WIDTH = 12;
    final static int WORLD_HEIGHT = 18;
    final static int SCORE_INCREMENT = 10;
    final static float TICK_INITIAL = 0.5f;
    final static float TICK_DECREMENT = 0.0155f;

    public Snake snake;
    public Fruit fruit;
    public boolean gameOver = false;
    public int score = 0;

    boolean[][] mFields = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random mRandom = new Random();
    float mTickTime = 0;
    float tick = TICK_INITIAL;

    public World() {
        snake = new Snake();
        placeFruit();
    }

    private void placeFruit() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                mFields[x][y] = false;
            }
        }

        int length = snake.snakeParts.size();
        for (int i = 0; i < length; i++) {
            SnakePart part = snake.snakeParts.get(i);
            mFields[part.x][part.y] = true;
        }

        int fruitX = mRandom.nextInt(WORLD_WIDTH);
        int fruitY = mRandom.nextInt(WORLD_HEIGHT);
        while (true) {
            if (mFields[fruitX][fruitY] == false) {
                break;
            }
            fruitX += 1;
            if (fruitX >= WORLD_WIDTH) {
                fruitX = 0;
                fruitY += 1;
                if (fruitY > WORLD_HEIGHT) {
                    fruitY = 0;
                }
            }
        }
        fruit= new Fruit(fruitX, fruitY, mRandom.nextInt(3));
    }

    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        mTickTime += deltaTime;

        while (mTickTime > tick) {
            mTickTime -= tick;
            snake.advance();
            if (snake.checkBitten()) {
                gameOver = true;
                return;
            }

            SnakePart head = snake.snakeParts.get(0);
            if (head.x == fruit.x && head.y == fruit.y) {
                score += SCORE_INCREMENT;
                snake.eat();
                if (snake.snakeParts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                } else {
                    placeFruit();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}
