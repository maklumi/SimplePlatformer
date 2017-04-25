package com.jga.platformer.screen.game.world


class GameWorld {

    var isDrawGrid = true
        private set
    var isDrawDebug = false
        private set

    val toggleDrawGrid: Unit
        get() {
            isDrawGrid = !isDrawGrid
        }

    val toggleDrawDebug: Unit
        get() {
            isDrawDebug = !isDrawDebug
        }


    fun update(delta: Float) {

    }


}