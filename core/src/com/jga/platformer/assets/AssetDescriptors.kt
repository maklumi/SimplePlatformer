package com.jga.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader


object AssetDescriptors {

    private val MAP_PARAMS = TmxMapLoader.Parameters().apply {
        convertObjectToTileSpace = true
    }

    val LEVEL_01 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_01, TiledMap::class.java, MAP_PARAMS)


}