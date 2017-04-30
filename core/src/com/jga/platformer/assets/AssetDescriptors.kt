package com.jga.platformer.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.Array


object AssetDescriptors {

    private val MAP_PARAMS = TmxMapLoader.Parameters().apply {
        convertObjectToTileSpace = true
    }

    val LEVEL_01 = AssetDescriptor<TiledMap>(AssetPaths.LEVEL_01, TiledMap::class.java, MAP_PARAMS)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas::class.java)

    val FONT = AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont::class.java)

    val BACKGROUND = AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture::class.java)

    val ALL = Array<AssetDescriptor<*>>().apply {
        addAll(LEVEL_01,
                GAME_PLAY,
                FONT,
                BACKGROUND)
    }
}