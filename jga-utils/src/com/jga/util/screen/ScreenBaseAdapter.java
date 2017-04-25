package com.jga.util.screen;

import com.badlogic.gdx.InputProcessor;

/**
 * @author goran on 1/12/2016.
 */
public abstract class ScreenBaseAdapter implements ScreenBase {

    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
