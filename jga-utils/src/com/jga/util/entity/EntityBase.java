package com.jga.util.entity;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jga.util.script.EntityScript;
import com.jga.util.shape.ShapeUtils;

/**
 * Represents base class for entities / game objects.
 *
 * @author goran
 */

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected Polygon bounds = new Polygon();

    private final Array<EntityScript<EntityBase>> scripts = new Array<EntityScript<EntityBase>>();

    // == constructors ==
    public EntityBase() {
        bounds.setPosition(x, y);
        bounds.setVertices(createVertices());
    }

    // == public methods ==

    /**
     * Updates game object, called every frame.
     *
     * @param delta The delta time.
     */
    public void update(float delta) {
        for (int i = 0; i < scripts.size; i++) {
            EntityScript script = scripts.get(i);

            if (script.isFinished()) {
                scripts.removeIndex(i);
            } else {
                script.update(delta);
            }
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setSize(float wh) {
        width = wh;
        height = wh;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Polygon getBounds() {
        return bounds;
    }

    public Rectangle getBoundingRectangle() {
        return bounds.getBoundingRectangle();
    }

    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setVertices(createVertices());
    }

    public void addScript(EntityScript toAdd) {
        scripts.add(toAdd);
        toAdd.added(this);
    }

    public void removeScript(EntityScript toRemove) {
        scripts.removeValue(toRemove, true);
        toRemove.removed(this);
    }

    // == private methods ==
    protected float[] createVertices() {
        return ShapeUtils.createRectangle(width, height);
    }
}
