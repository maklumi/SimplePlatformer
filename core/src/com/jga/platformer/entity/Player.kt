package com.jga.platformer.entity

import com.jga.platformer.config.GameConfig
import com.jga.util.entity.EntityBase


class Player : EntityBase() {

    var state = PlayerState.FALLING
    var velocityX = 0f
    var velocityY = 0f

    override fun update(delta: Float) {
        super.update(delta)

        velocityY += GameConfig.GRAVITY_Y * delta
        setX(x + velocityX * delta)
        setY(y + velocityY * delta)
    }

    fun jump() {
        state = PlayerState.JUMPING
        velocityY = GameConfig.JUMP_VELOCITY
    }
}