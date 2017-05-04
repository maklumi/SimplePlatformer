package com.jga.platformer.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.LayerNames.COLLECTIBLES
import com.jga.platformer.assets.LayerNames.HAZARDS
import com.jga.platformer.assets.LayerNames.PLATFORMS
import com.jga.platformer.assets.LayerNames.PLAYER
import com.jga.platformer.assets.MapObjectNames
import com.jga.platformer.config.GameConfig.COIN_SIZE
import com.jga.platformer.config.GameConfig.PLAYER_SIZE
import com.jga.platformer.entity.Coin
import com.jga.platformer.entity.Platform
import com.jga.platformer.entity.Player
import com.jga.platformer.entity.WaterHazard
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.Validate
import com.jga.util.entity.EntityBase
import com.jga.util.map.MapUtils

class EntityFactory(val assetManager: AssetManager) {

    fun setupGameWorld(world: GameWorld, map: TiledMap): GameWorld {
        Validate.notNull(world)
        Validate.notNull(map)

        // process layers
        processLayer(map, HAZARDS, world)
        processLayer(map, PLATFORMS, world)
        processLayer(map, PLAYER, world)
        processLayer(map, COLLECTIBLES, world)

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

        if (MapObjectNames.PLAYER == mapObject.name) {
            val player = createPlayer(mapObject)
            world.player = player
        }

        if (MapObjectNames.COIN == mapObject.name) {
            val coin = createCoin(mapObject)
            world.coins.add(coin)
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

    private fun createPlayer(mapObject: MapObject): Player {
        val isTileMapObject = TiledMapTileMapObject::class.java.isInstance(mapObject)
        if (!isTileMapObject) {
            throw IllegalArgumentException("Player spawn position is not TiledMapTileMapObject, but ${mapObject.javaClass.simpleName}")
        }
        val tileMapObject = mapObject as TiledMapTileMapObject

        val player = Player()
        player.setPosition(tileMapObject.x, tileMapObject.y)
        player.setStartingPosition(tileMapObject.x, tileMapObject.y)
        player.setSize(PLAYER_SIZE)
        return player
    }

    private fun createCoin(mapObject: MapObject): Coin {
        mapObject as TiledMapTileMapObject

        val coin = Coin().apply {
            setPosition(mapObject.x, mapObject.y)
            setSize(COIN_SIZE)
        }

        return coin
    }

    companion object {
        private val log = Logger(EntityFactory::class.java.simpleName, Logger.DEBUG)
    }
}
