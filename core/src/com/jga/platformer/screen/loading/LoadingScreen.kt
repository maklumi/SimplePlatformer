package com.jga.platformer.screen.loading

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.utils.Array
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.config.GameConfig
import com.jga.platformer.screen.game.GameScreen
import com.jga.util.game.GameBase
import com.jga.util.screen.loading.LoadingScreenBase


class LoadingScreen(gameBase: GameBase) : LoadingScreenBase(gameBase) {

    override fun getAssetDescriptors(): Array<AssetDescriptor<*>> {
        return AssetDescriptors.ALL
    }

    override fun onLoadDone() {
        game.setScreen(GameScreen(game))
    }

    override fun getHudWidth(): Float {
        return GameConfig.HUD_WIDTH
    }

    override fun getHudHeight(): Float {
        return GameConfig.HUD_HEIGHT
    }
}