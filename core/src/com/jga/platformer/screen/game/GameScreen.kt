package com.jga.platformer.screen.game

import com.jga.platformer.SimplePlatformerGame
import com.jga.util.screen.ScreenBaseAdapter


class GameScreen(val game: SimplePlatformerGame) : ScreenBaseAdapter() {

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        super.dispose()
    }
}