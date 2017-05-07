package com.jga.platformer.level

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.AssetDescriptors

class LevelController(val assetManager: AssetManager) {

    private val log = Logger(LevelController::class.java.simpleName, Logger.DEBUG)

    private val levelDescriptors = Array<AssetDescriptor<TiledMap>>()

    var currentLevel: AssetDescriptor<TiledMap>? = null

    init {
        levelDescriptors.addAll(
                AssetDescriptors.LEVEL_01,
                AssetDescriptors.LEVEL_02,
                AssetDescriptors.LEVEL_03
        )
    }

    fun getCurrentMap(): TiledMap {
        return assetManager[currentLevel]
    }

    fun loadRandomLevel() {
        unload()
        randomLevel()
        load()
    }

    private fun randomLevel() {
        levelDescriptors.shuffle()
        currentLevel = levelDescriptors.random()
    }

    private fun unload() {
        if (currentLevel != null) {
            assetManager.unload(currentLevel!!.fileName)
        }
    }

    private fun load() {
        assetManager.load(currentLevel)
        assetManager.finishLoading()
    }
}
