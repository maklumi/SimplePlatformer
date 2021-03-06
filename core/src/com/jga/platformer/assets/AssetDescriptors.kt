package com.jga.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    val MAP_PARAMS = TmxMapLoader.Parameters().apply {
        convertObjectToTileSpace = true
    }

    val LEVEL_01 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_01, TiledMap::class.java, MAP_PARAMS)
    val LEVEL_02 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_02, TiledMap::class.java, MAP_PARAMS)
    val LEVEL_03 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_03, TiledMap::class.java, MAP_PARAMS)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas::class.java)

    val FONT = AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont::class.java)

    val BACKGROUND = AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture::class.java)

    val SKIN = AssetDescriptor<Skin>(AssetPaths.SKIN, Skin::class.java)

    val ALL = Array<AssetDescriptor<*>>().apply {
        addAll(GAME_PLAY,
                FONT,
                SKIN,
                BACKGROUND)
    }
}