package com.jga.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.LayerNames.HAZARDS
import com.jga.platformer.config.GameConfig.WORLD_WIDTH
import com.jga.platformer.entity.WaterHazard
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.Validate
import com.jga.util.map.MapUtils

class EntityFactory(val assetManager: AssetManager) {

    fun createGameWorld(): GameWorld {
        val map = assetManager[AssetDescriptors.LEVEL_01]
        MapUtils.debugMapProperties(map.properties)

        val hazardsLayer = map.layers[HAZARDS]

        Validate.notNull(hazardsLayer, "Layer with name $HAZARDS not found")

        MapUtils.debugMapProperties(hazardsLayer.properties)

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
