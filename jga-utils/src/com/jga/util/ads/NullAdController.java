package com.jga.util.ads;

import com.badlogic.gdx.utils.Logger;

/**
 * @author goran on 16/11/2016.
 */
public final class NullAdController implements AdController {

    // == constants ==
    public static final AdController INSTANCE = new NullAdController();

    private static final Logger log = new Logger(NullAdController.class.getName(), Logger.DEBUG);

    // == constructors ==
    private NullAdController() {
    }

    // == public methods ==
    @Override
    public void showBanner() {
        log.debug("showBanner");
    }

    @Override
    public void showInterstitial() {
        log.debug("showInterstitial");
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}
