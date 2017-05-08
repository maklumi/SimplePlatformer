package com.jga.util.screen.loading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.jga.util.GdxUtils;
import com.jga.util.game.GameBase;
import com.jga.util.screen.ScreenBaseAdapter;
import com.jga.util.viewport.ViewportManager;

/**
 * Base Screen class for "Loading Screens" with progress bar.
 *
 * @author goran on 29/11/2016.
 */
public abstract class LoadingScreenBase extends ScreenBaseAdapter {

    // == constants ==
    private static final float DEFAULT_PROGRESS_BAR_HEIGHT = 60f;


    // == attributes ==
    private float progressBarWidth;
    private float progressBarHeight;

    private float hudWidth;
    private float hudHeight;

    protected final GameBase game;
    protected final AssetManager assetManager;

    protected final ViewportManager viewportManager;

    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;

    private boolean changeScreen;

    // == constructors ==
    protected LoadingScreenBase(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
    }

    // == abstract methods ==
    protected abstract Array<AssetDescriptor> getAssetDescriptors();

    protected abstract void onLoadDone();

    // == public methods ==
    @Override
    public void show() {
        hudWidth = viewportManager.getHudWidth();
        hudHeight = viewportManager.getHudHeight();
        progressBarWidth = getProgressBarWidth();
        progressBarHeight = getProgressBarHeight();

        renderer = new ShapeRenderer();

        for (AssetDescriptor descriptor : getAssetDescriptors()) {
            assetManager.load(descriptor);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewportManager.applyHud();
        renderer.setProjectionMatrix(viewportManager.getHudCombined());
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            onLoadDone();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewportManager.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // == private methods ==
    protected float getProgressBarWidth() {
        return hudWidth / 2f;
    }

    protected float getProgressBarHeight() {
        return DEFAULT_PROGRESS_BAR_HEIGHT;
    }

    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (hudWidth - progressBarWidth) / 2f;
        float progressBarY = (hudHeight - progressBarHeight) / 2f;

        renderer.rect(progressBarX, progressBarY,
                progress * progressBarWidth, progressBarHeight);
    }
}