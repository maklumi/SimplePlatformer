package com.jga.platformer.screen.game.world

import com.badlogic.gdx.utils.Array
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

    fun update(delta: Float) {

    }

    fun addWaterHazard(hazard: WaterHazard) {
        waterHazards.add(hazard)
    }


}