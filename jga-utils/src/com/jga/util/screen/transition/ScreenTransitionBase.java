package com.jga.util.screen.transition;

/**
 * @author goran on 1/12/2016.
 */
public abstract class ScreenTransitionBase implements ScreenTransition {

    // == attributes ==
    protected final float duration;

    // == constructors ==
    public ScreenTransitionBase(float duration) {
        this.duration = duration;
    }

    // == public methods ==
    @Override
    public float getDuration() {
        return duration;
    }
}
