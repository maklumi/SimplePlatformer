package com.jga.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.LayerNames.HAZARDS
import com.jga.platformer.assets.MapObjectNames
import com.jga.platformer.entity.WaterHazard
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.Validate
import com.jga.util.map.MapUtils

class EntityFactory(val assetManager: AssetManager) {

    fun createGameWorld(): GameWorld {
        val world = GameWorld()

        val map = assetManager[AssetDescriptors.LEVEL_01]
        MapUtils.debugMapProperties(map.properties)

        val hazardsLayer = map.layers[HAZARDS]

        Validate.notNull(hazardsLayer, "Layer with name $HAZARDS not found")

        MapUtils.debugMapProperties(hazardsLayer.properties)

        val mapObjects = hazardsLayer.objects

        val iterator = mapObjects.iterator()
        while (iterator.hasNext()) {
            val mapObject = iterator.next()
            processMapObject(mapObject, world)
            log.debug("mapObject= $mapObject")
//            MapUtils.debugMapProperties(mapObject.properties)

//            if (mapObject is RectangleMapObject) {
//                log.debug("rectangle= ${mapObject.rectangle}")
//            }
        }
        return world
    }

    private fun processMapObject(mapObject: MapObject, world: GameWorld) {
        val mapObjectName = mapObject.name

        if (MapObjectNames.HAZARD == mapObjectName) {
            val waterHazard = createWaterHazard(mapObject)
            world.waterHazards.add(waterHazard)
        }
    }

    private fun createWaterHazard(mapObject: MapObject): WaterHazard {
        val isRectangleMapObject = RectangleMapObject::class.java.isInstance(mapObject)

        if (!isRectangleMapObject) {
            // throw exception if water hazard is not represented by rectangle
            throw IllegalArgumentException("Water hazard needs to be represented by rectangle")
        }

        val rectangleMapObject = mapObject as RectangleMapObject
        val rectangle = rectangleMapObject.rectangle

        val waterHazard = WaterHazard().apply {
            setPosition(rectangle.x, rectangle.y)
            setSize(rectangle.width, rectangle.height)
        }
        return waterHazard
    }

    companion object {
        private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)
    }
}
