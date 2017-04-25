package com.jga.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.maps.tiled.TiledMap


object AssetDescriptors {

    val LEVEL_01 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_01, TiledMap::class.java)

}