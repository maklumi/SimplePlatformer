package com.jga.util.ads;

/**
 * Created by goran on 14/10/2016.
 */

public interface AdController {

    /**
     * Show banner Ad.
     */
    void showBanner();

    /**
     * Show interstitial Ad.
     */
    void showInterstitial();

    /**
     * Checks if network is connected.
     *
     * @return True if connected, false otherwise.
     */
    boolean isNetworkConnected();
}
