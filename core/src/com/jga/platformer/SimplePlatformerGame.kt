package com.jga.platformer

import com.jga.platformer.screen.game.GameScreen
import com.jga.util.ads.AdController
import com.jga.util.game.GameBase

class SimplePlatformerGame(adController: AdController) : GameBase(adController) {

    override fun postCreate() {
        setScreen(GameScreen(this))
    }
}
