package com.jga.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.LayerNames.HAZARDS
import com.jga.platformer.assets.LayerNames.PLATFORMS
import com.jga.platformer.assets.MapObjectNames
import com.jga.platformer.entity.Platform
import com.jga.platformer.entity.WaterHazard
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.Validate
import com.jga.util.entity.EntityBase
import com.jga.util.map.MapUtils

class EntityFactory(val assetManager: AssetManager) {

    fun createGameWorld(): GameWorld {
        val world = GameWorld()

        val map = assetManager[AssetDescriptors.LEVEL_01]

        // process layers
        processLayer(map, HAZARDS, world)
        processLayer(map, PLATFORMS, world)

        return world
    }

    private fun processLayer(map: TiledMap, layerName: String, gameWorld: GameWorld) {
        val layers = map.layers
        val layer = layers[layerName]
        Validate.notNull(layer, "Layer with name $layerName not found")
        MapUtils.debugMapProperties(layer.properties)
        val mapObjects = layer.objects
        mapObjects.forEach {
            processMapObject(it, gameWorld)
            log.debug("mapObject=$it")
        }
    }

    private fun processMapObject(mapObject: MapObject, world: GameWorld) {
        if (MapObjectNames.HAZARD == mapObject.name) {
            val waterHazard = createWaterHazard(mapObject)
            world.waterHazards.add(waterHazard)
        }

        if (MapObjectNames.PLATFORM == mapObject.name) {
            val platform = createPlatform(mapObject)
            world.platforms.add(platform)
        }
    }

    private fun <T : EntityBase> initializeEntityObject(entity: T, mapObject: MapObject) {
        val rectangle = (mapObject as RectangleMapObject).rectangle
        entity.apply {
            setPosition(rectangle.x, rectangle.y)
            setSize(rectangle.width, rectangle.height)
        }
    }

    private fun createWaterHazard(mapObject: MapObject): WaterHazard {
        val waterHazard = WaterHazard()
        initializeEntityObject(waterHazard, mapObject)
        return waterHazard
    }

    private fun createPlatform(mapObject: MapObject): Platform {
        val platform = Platform()
        initializeEntityObject(platform, mapObject)
        return platform
    }


    companion object {
        private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)
    }
}
