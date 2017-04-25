package com.jga.util.screen.transition.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.jga.util.GdxUtils;
import com.jga.util.screen.transition.ScreenTransitionBase;

/**
 * @author goran
 */
public class FadeScreenTransition extends ScreenTransitionBase {

    private Interpolation interpolation;

    // == constructors ==
    public FadeScreenTransition(float duration) {
        super(duration);
    }

    // == public methods ==
    @Override
    public void render(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percentage) {

        // interpolate percentage
        percentage = Interpolation.fade.apply(percentage);

        // clear screen
        GdxUtils.clearScreen();

        batch.begin();

        Color oldColor = batch.getColor().cpy();

        // draw current screen
        drawTexture(batch, currentScreenTexture, 1f - percentage);

        // draw next screen
        drawTexture(batch, nextScreenTexture, percentage);

        batch.setColor(oldColor);

        batch.end();
    }

    private void drawTexture(SpriteBatch batch, Texture texture, float a) {
        int width = texture.getWidth();
        int he = texture.getHeight();

        batch.setColor(1, 1, 1, a); // white with transparency
        batch.draw(texture,
                0, 0, // x,y
                0, 0, // origin x,y
                width, he, // width/height
                1, 1, // scale x,y
                0, // rotation
                0, 0, // src x,y
                width, he, // src width/height
                false, true // flip x/y
        );
    }
}
