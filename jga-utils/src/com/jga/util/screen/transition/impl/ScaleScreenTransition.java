package com.jga.util.screen.transition.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.jga.util.GdxUtils;
import com.jga.util.Validate;
import com.jga.util.screen.transition.ScreenTransitionBase;

/**
 * @author goran on 3/12/2016.
 */
public class ScaleScreenTransition extends ScreenTransitionBase {

    // == attributes ==
    private boolean scaleOut;
    private Interpolation interpolation;

    // == constructors ==
    public ScaleScreenTransition(float duration) {
        this(duration, false);
    }

    public ScaleScreenTransition(float duration, boolean scaleOut) {
        this(duration, scaleOut, Interpolation.linear);
    }

    public ScaleScreenTransition(float duration, boolean scaleOut, Interpolation interpolation) {
        super(duration);

        Validate.notNull(interpolation);

        this.scaleOut = scaleOut;
        this.interpolation = interpolation;
    }

    // == public methods ==
    @Override
    public void render(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percentage) {

        // interpolate percentage
        percentage = interpolation.apply(percentage);

        float scale = 1 * (1 - percentage);

        if (scaleOut) {
            scale = 1 * percentage;
        }

        // drawing order depends on scale type (in or out)
        Texture topTexture = scaleOut ? nextScreenTexture : currentScreenTexture;
        Texture bottomTexture = scaleOut ? currentScreenTexture : nextScreenTexture;

        // drawing
        GdxUtils.clearScreen();
        batch.begin();

        // draw bottom texture
        int bottomTextureWidth = bottomTexture.getWidth();
        int bottomTextureHeight = bottomTexture.getHeight();

        batch.draw(bottomTexture,
                0, 0,
                bottomTextureWidth / 2f, bottomTextureHeight / 2f,
                bottomTextureWidth, bottomTextureHeight,
                1, 1,
                0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                false, true);

        // draw top texture
        int topTextureWidth = topTexture.getWidth();
        int topTextureHeight = topTexture.getHeight();

        batch.draw(topTexture,
                0, 0,
                topTextureWidth / 2f, topTextureHeight / 2f,
                topTextureWidth, topTextureHeight,
                scale, scale,
                0,
                0, 0,
                topTextureWidth, topTextureHeight,
                false, true);

        batch.end();
    }
}
