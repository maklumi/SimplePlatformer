package com.jga.platformer.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger

object LevelController {

    private val log = Logger(LevelController::class.java.simpleName, Logger.DEBUG)

    private val LEVEL_PATH = "level"
    private val DOT_TMX = ".tmx"
    private val LEVEL_DIR: FileHandle = Gdx.files.internal(LEVEL_PATH)

    private val levelDescriptors = Array<AssetDescriptor<TiledMap>>()

    val currentLevel: AssetDescriptor<TiledMap>

    init {
        val levelFileHandles = LEVEL_DIR.list(DOT_TMX)

        for (fileHandle in levelFileHandles) {
            log.debug("path= ${fileHandle.path()}")
            val descriptor = AssetDescriptor<TiledMap>(fileHandle.path(), TiledMap::class.java)
            levelDescriptors.add(descriptor)
        }
        log.debug("levelDescriptors-size ${levelDescriptors.size}")

        currentLevel = levelDescriptors.random()
    }
}
