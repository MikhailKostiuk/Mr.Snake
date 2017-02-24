package com.example.mrsnake;


import java.util.ArrayList;
import java.util.List;

public class Snake {

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    public List<SnakePart> snakeParts = new ArrayList<>();
    public int direction;

    public Snake() {
        direction = UP;
        snakeParts.add(new SnakePart(6, 8));
        snakeParts.add(new SnakePart(6, 9));
    }

    public void turnLeft() {
        direction += 1;
        if (direction > RIGHT) {
            direction = UP;
        }
    }

    public void turnRight() {
        direction -= 1;
        if (direction < UP) {
            direction = RIGHT;
        }
    }

    public void eat() {
        SnakePart end = snakeParts.get(snakeParts.size() - 1);
        snakeParts.add(new SnakePart(end.x, end.y));
    }

    public void advance() {
        SnakePart head = snakeParts.get(0);

        int length = snakeParts.size() - 1;
        for (int i = length; i > 0; i--) {
            SnakePart before = snakeParts.get(i - 1);
            SnakePart part = snakeParts.get(i);
            part.x = before.x;
            part.y = before.y;
        }

        if (direction == UP) {
            head.y -= 1;
        }
        if (direction ==  LEFT) {
            head.x -= 1;
        }
        if (direction == DOWN) {
            head.y += 1;
        }
        if (direction == RIGHT) {
            head.x += 1;
        }

        if (head.x < 0) {
            head.x = 11;
        }
        if (head.x > 11) {
            head.x = 0;
        }
        if (head.y < 0) {
            head.y = 17;
        }
        if (head.y > 17) {
            head.y = 0;
        }
    }

    public boolean checkBitten() {
        int length = snakeParts.size();
        SnakePart head = snakeParts.get(0);
        for (int i = 1; i < length; i++) {
            SnakePart part = snakeParts.get(i);
            if (part.x == head.x && part.y == head.y) {
                return true;
            }
        }
        return false;
    }
}
