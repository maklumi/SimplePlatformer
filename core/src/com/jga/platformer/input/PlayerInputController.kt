package com.jga.platformer.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.jga.platformer.config.GameConfig
import com.jga.platformer.screen.game.world.GameWorld

class PlayerInputController(val world: GameWorld) {

    fun update(delta: Float) {
        var velocityX = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -GameConfig.MOVE_VELOCITY
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = GameConfig.MOVE_VELOCITY
        }
        // set player velocity
        world.player.velocityX = velocityX
    }
}
