package com.jga.platformer

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.jga.platformer.level.LevelController
import com.jga.platformer.screen.loading.LoadingScreen
import com.jga.util.ads.AdController
import com.jga.util.game.GameBase

class SimplePlatformerGame(adController: AdController) : GameBase(adController) {

    lateinit var levelController: LevelController

    override fun postCreate() {
        val assetManager = assetManager
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader())
        levelController = LevelController(assetManager)
        setScreen(LoadingScreen(this))
    }
}
