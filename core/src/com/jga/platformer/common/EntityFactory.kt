package com.jga.platformer.common

import com.jga.platformer.config.GameConfig.WORLD_WIDTH
import com.jga.platformer.entity.WaterHazard
import com.jga.platformer.screen.game.world.GameWorld

class EntityFactory {

    fun createGameWorld(): GameWorld {
        val world = GameWorld().apply {
            addWaterHazard(createWaterHazard())
        }
        return world
    }

    private fun createWaterHazard(): WaterHazard {
        val waterHazard = WaterHazard().apply {
            setPosition(0f, 0f)
            setSize(WORLD_WIDTH, 1.0f)
        }
        return waterHazard
    }
}
