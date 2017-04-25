package com.jga.util.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.util.ads.AdController;
import com.jga.util.screen.ScreenBase;
import com.jga.util.screen.transition.ScreenTransition;

/**
 * Custom {@link ApplicationListener} class with {@link ScreenTransition} support.
 *
 * @author goran
 */
public abstract class GameBase implements ApplicationListener {

    // == attributes ==
    private final AdController adController;
    private AssetManager assetManager;
    private SpriteBatch batch;

    // viewport for transitions only
    private Viewport viewport;

    private ScreenBase currentScreen;
    private ScreenBase nextScreen;

    private FrameBuffer currentFrameBuffer;
    private FrameBuffer nextFrameBuffer;

    private float time;
    private ScreenTransition transition;
    private boolean renderedToTexture;
    private boolean transitionInProgress;

    // == constructors ==
    public GameBase(AdController adController) {
        this.adController = adController;
    }

    // == abstract methods ==
    public abstract void postCreate();

    // == public methods ==
    @Override
    public final void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        postCreate();
    }

    public void setScreen(ScreenBase screen) {
        setScreen(screen, null);
    }

    public void setScreen(ScreenBase screen, ScreenTransition transition) {

        if (transitionInProgress) {
            return;
        }

        if (currentScreen == screen) {
            return;
        }

        if (nextScreen == screen) {
            return;
        }

        this.transition = transition;

        // screen size
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        System.out.println("setScreen width= " + width + " height= " + height);

        // create frame buffers
        currentFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false); // depth false used in 3D
        nextFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        // disable input processors later enabled when transition is finished
        Gdx.input.setInputProcessor(null);

        // start new transition
        nextScreen = screen;
        nextScreen.show();
        nextScreen.resize(width, height);

        if (currentScreen != null) {
            currentScreen.pause();
        }

        nextScreen.pause();
        time = 0;
    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (nextScreen == null) {
            // no transition
            if (currentScreen != null) {
                currentScreen.render(delta);
            }
        } else {
            // set flag to indicate that transition is in progress
            transitionInProgress = true;

            float duration = getDuration();
            time = Math.min(time + delta, duration);

            // render to texture only once for more performance
            if (!renderedToTexture) {
                renderScreensToTexture();
                renderedToTexture = true;
            }

            // update transition
            updateTransition();
        }
    }


    @Override
    public void resize(int width, int height) {
        if (currentScreen != null) {
            currentScreen.resize(width, height);
        }

        if (nextScreen != null) {
            nextScreen.resize(width, height);
        }

        // set world size to width/height we want to keep 1:1 pixel per unit ratio
        viewport.setWorldSize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if (currentScreen != null) {
            currentScreen.pause();
        }
    }

    @Override
    public void resume() {
        if (currentScreen != null) {
            currentScreen.resume();
        }
    }

    @Override
    public void dispose() {

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        if (nextScreen != null) {
            nextScreen.dispose();
        }

        if (currentFrameBuffer != null) {
            currentFrameBuffer.dispose();
        }

        if (nextFrameBuffer != null) {
            nextFrameBuffer.dispose();
        }

        currentScreen = null;
        nextScreen = null;

        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AdController getAdController() {
        return adController;
    }

    // == private methods ==
    private void renderScreensToTexture() {
        // render current screen to FBO (texture)
        if (currentScreen != null) {
            currentFrameBuffer.begin();
            currentScreen.render(0); // 0 to render 1st frame
            currentFrameBuffer.end();
        }

        // render next screen to FBO (texture)
        nextFrameBuffer.begin();
        nextScreen.render(0); // 0 to render first frame
        nextFrameBuffer.end();
    }

    private void updateTransition() {
        if (transition == null || isTransitionFinished()) {
            // just finished or no transition set
            if (currentScreen != null) {
                currentScreen.hide();
            }

            // resume and enable input
            nextScreen.resume();
            Gdx.input.setInputProcessor(nextScreen.getInputProcessor());

            // switch screens and reset
            currentScreen = nextScreen;
            nextScreen = null;
            transition = null;
            currentFrameBuffer.dispose();
            nextFrameBuffer.dispose();
            currentFrameBuffer = null;
            nextFrameBuffer = null;
            renderedToTexture = false;
            transitionInProgress = false;
            return;
        }

        // calculate percentage
        float percentage = time / getDuration(); // it will be 0-1

        // grab textures from FBOs and render transition
        // NOTE: these textures are auto disposed when FBO is disposed
        Texture currentScreenTexture = currentFrameBuffer.getColorBufferTexture();
        Texture nextScreenTexture = nextFrameBuffer.getColorBufferTexture();

        // render transition to screen
        batch.setProjectionMatrix(viewport.getCamera().combined);
        transition.render(batch, currentScreenTexture, nextScreenTexture, percentage);
    }

    private float getDuration() {
        return transition == null ? 0 : transition.getDuration();
    }

    private boolean isTransitionFinished() {
        return time >= getDuration();
    }
}
