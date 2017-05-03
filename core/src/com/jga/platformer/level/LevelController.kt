package com.jga.platformer.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.AssetDescriptors

class LevelController(val assetManager: AssetManager) {

    private val log = Logger(LevelController::class.java.simpleName, Logger.DEBUG)

    private val LEVEL_PATH = "level"
    private val DOT_TMX = ".tmx"
    private val LEVEL_DIR: FileHandle = Gdx.files.internal(LEVEL_PATH)

    private val levelDescriptors = Array<AssetDescriptor<TiledMap>>()

    var currentLevel: AssetDescriptor<TiledMap>? = null

    init {
        val levelFileHandles = LEVEL_DIR.list(DOT_TMX)

        for (fileHandle in levelFileHandles) {
            log.debug("path= ${fileHandle.path()}")
            val descriptor = AssetDescriptor<TiledMap>(fileHandle.path(), TiledMap::class.java, AssetDescriptors.MAP_PARAMS)
            levelDescriptors.add(descriptor)
        }
        log.debug("levelDescriptors-size ${levelDescriptors.size}")

        loadRandomLevel()
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
