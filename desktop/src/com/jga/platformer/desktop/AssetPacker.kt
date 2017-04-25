package com.jga.platformer.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {

    val RAW_ASSETS_PATH = "desktop/assets-raw"
    val ASSETS_PATH = "core/assets"

    @JvmStatic
    fun main(arg: Array<String>) {
        TexturePacker.process(
                RAW_ASSETS_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay"
        )
    }
}

