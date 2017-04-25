package com.jga.platformer.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.jga.platformer.SimplePlatformerGame
import com.jga.platformer.config.GameConfig
import com.jga.util.ads.NullAdController

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            width = GameConfig.WIDTH
            height = GameConfig.HEIGHT
        }
        LwjglApplication(SimplePlatformerGame(NullAdController.INSTANCE), config)
    }
}
