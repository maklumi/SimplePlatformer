package com.jga.util.parallax;

import com.badlogic.gdx.math.Rectangle;
import com.jga.util.Direction;

/**
 * Class that manages parallax layer movement.
 * Useful for moving backgrounds.
 *
 * @author goran
 */
public class ParallaxLayer {

    // == attributes ==
    private final Rectangle firstRegionBounds;
    private final Rectangle secondRegionBounds;

    private float speed = 1;
    private Direction direction = Direction.LEFT;

    private float startX;
    private float startY;

    // dimension
    private float width = 1;
    private float height = 1;

    // == constructors ==
    public ParallaxLayer() {
        firstRegionBounds = new Rectangle(startX, startY, width, height);
        secondRegionBounds = new Rectangle(startX + width, startY, width, height);
    }

    // == public methods ==
    public void update(float delta) {
        if (boundsReached(delta)) {
            resetBounds();
            return;
        }

        if (direction.isLeft()) {
            firstRegionBounds.x -= delta * speed;
            secondRegionBounds.x -= delta * speed;
        } else if (direction.isRight()) {
            firstRegionBounds.x += delta * speed;
            secondRegionBounds.x += delta * speed;
        } else if (direction.isUp()) {
            firstRegionBounds.y += delta * speed;
            secondRegionBounds.y += delta * speed;
        } else if (direction.isDown()) {
            firstRegionBounds.y -= delta * speed;
            secondRegionBounds.y -= delta * speed;
        }
    }

    public Rectangle getFirstRegionBounds() {
        return firstRegionBounds;
    }

    public Rectangle getSecondRegionBounds() {
        return secondRegionBounds;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setStartPosition(float startX, float startY) {
        this.startX = startX;
        this.startY = startY;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        updateBounds();
    }

    public Direction getDirection() {
        return direction;
    }

    // == private methods ==
    private void updateBounds() {
        // assume left since left is default
        firstRegionBounds.set(startX, startY, width, height);
        secondRegionBounds.set(startX + width, startY, width, height);

        if (direction.isRight()) {
            secondRegionBounds.set(startX - width, startY, width, height);
        } else if (direction.isUp()) {
            secondRegionBounds.set(startX, startY - height, width, height);
        } else if (direction.isDown()) {
            secondRegionBounds.set(startX, startY + height, width, height);
        }

    }

    private boolean boundsReached(float delta) {
        if (direction.isRight()) {
            float nextPos = secondRegionBounds.x + delta * speed;
            return nextPos >= 0f;
        } else if (direction.isUp()) {
            float nextPos = secondRegionBounds.y + delta * speed;
            return nextPos >= 0f;
        } else if (direction.isDown()) {
            float nextPos = secondRegionBounds.y - delta * speed;
            return nextPos <= 0f;
        }

        // left
        float nextPos = secondRegionBounds.x - delta * speed;
        return nextPos <= 0f;
    }

    private void resetBounds() {
        // assume left since left is default
        firstRegionBounds.set(0, 0, width, height);
        secondRegionBounds.set(width, 0, width, height);

        if (direction.isRight()) {
            secondRegionBounds.set(-width, 0, width, height);
        } else if (direction.isUp()) {
            secondRegionBounds.set(0, -height, width, height);
        } else if (direction.isDown()) {
            secondRegionBounds.set(0, height, width, height);
        }
    }
}
