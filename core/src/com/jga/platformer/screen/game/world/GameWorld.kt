package com.jga.platformer.screen.game.world

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Array
import com.jga.platformer.common.GameState
import com.jga.platformer.config.GameConfig
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

    var score = 0
    var lives = GameConfig.LIVES_START

    var state = GameState.PLAYING

    fun update(delta: Float) {
        player.update(delta)

        blockPlayerFromLeavingWorld()
        checkCollision()

        // check if all coins are collected and set level to complete
        if (coins.size == 0) state = GameState.LEVEL_COMPLETE
    }

    val isGameOver: Boolean
        get() = state.isGameOver

    val isLevelComplete: Boolean
        get() = state.isLevelComplete

    fun playing() {
        state = GameState.PLAYING
    }

    fun clear() {
        platforms.clear()
        waterHazards.clear()
        coins.clear()
        player = Player()
    }

    private fun checkCollision() {
        // player - platform
        platforms.forEach { platform ->
            val overlaps = Intersector.overlapConvexPolygons(player.bounds, platform.bounds)
            val falling = player.state.isFalling

            if (overlaps && falling) {
                player.y = platform.y + platform.height
                player.jump()
            }
        }

        // player - coins
        coins.forEach { coin ->
            val overlaps = Intersector.overlapConvexPolygons(player.bounds, coin.bounds)
            if (overlaps) {
                coins.removeValue(coin, true)
                score += GameConfig.COIN_SCORE
            }
        }

        // player - hazards
        waterHazards.forEach { hazard ->
            val overlaps = Intersector.overlapConvexPolygons(player.bounds, hazard.bounds)
            if (overlaps) {
                player.die()
                lives--
                // spawn player again
                player.reset()
            }

            if (lives <= 0) {
                gameOver()
            }
        }

    }

    private fun gameOver() {
        state = GameState.GAME_OVER
    }

    private fun levelComplete() {
        state = GameState.LEVEL_COMPLETE
    }

    private fun blockPlayerFromLeavingWorld() {
        // left
        if (player.x < 0f) player.x = 0f

        // right
        val maxX = GameConfig.WORLD_WIDTH - player.width
        if (player.x > maxX) player.x = maxX

        // top
        val maxY = GameConfig.WORLD_HEIGHT - player.height
        if (player.y > maxY) player.y = maxY

    }
}