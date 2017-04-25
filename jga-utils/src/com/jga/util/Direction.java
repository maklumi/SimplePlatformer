package com.jga.util;

/**
 * Represents direction.
 *
 * @author goran
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    // == public methods ==
    public boolean isUp() {
        return this == UP;
    }

    public boolean isDown() {
        return this == DOWN;
    }

    public boolean isLeft() {
        return this == LEFT;
    }

    public boolean isRight() {
        return this == RIGHT;
    }
}
