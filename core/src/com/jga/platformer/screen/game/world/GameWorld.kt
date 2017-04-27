package com.jga.platformer.screen.game.world

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Array
import com.jga.platformer.entity.Coin
import com.jga.platformer.entity.Platform
import com.jga.platformer.entity.Player
import com.jga.platformer.entity.WaterHazard


class GameWorld {

    var isDrawGrid = true
        private set
    var isDrawDebug = true
        private set

    val toggleDrawGrid: Unit
        get() {
            isDrawGrid = !isDrawGrid
        }

    val toggleDrawDebug: Unit
        get() {
            isDrawDebug = !isDrawDebug
        }

    val waterHazards = Array<WaterHazard>()
    val platforms = Array<Platform>()
    var player = Player()
    val coins = Array<Coin>()

    fun update(delta: Float) {
        player.update(delta)

        checkCollision()
    }

    private fun checkCollision() {
        // player - platform
        platforms.forEach { platform ->
            if (Intersector.overlapConvexPolygons(player.bounds, platform.bounds)) {
                player.jump()
            }
        }

    }


}