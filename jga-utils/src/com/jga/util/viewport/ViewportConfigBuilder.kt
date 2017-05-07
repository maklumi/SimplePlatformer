package com.jga.util.viewport


class ViewportConfigBuilder {

    private val viewportConfig = ViewportConfig()

    fun hudSize(hudWidth: Float, hudHeight: Float): ViewportConfigBuilder {
        viewportConfig.hudWidth = hudWidth
        viewportConfig.hudHeight = hudHeight
        return this
    }

    fun worldSize(worldWidth: Float, worldHeight: Float): ViewportConfigBuilder {
        viewportConfig.worldWidth = worldWidth
        viewportConfig.worldHeight = worldHeight
        return this
    }

    fun build(): ViewportConfig {
        return viewportConfig
    }
}

