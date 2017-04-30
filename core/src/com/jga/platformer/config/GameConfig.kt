package com.jga.platformer.config

object GameConfig {

    // desktop only
    val WIDTH = 1280 // pixels
    val HEIGHT = 780 // pixels

    val WORLD_WIDTH = 20f // world units
    val WORLD_HEIGHT = 12f // world units

    val WORLD_CENTER_X = WORLD_WIDTH / 2f
    val WORLD_CENTER_Y = WORLD_HEIGHT / 2f

    val UNIT_SCALE = 1f / 64f

    val PLAYER_SIZE = 0.95f
    val COIN_SIZE = 0.5f

    val MOVE_VELOCITY = 3f
    val JUMP_VELOCITY = 9f
    val GRAVITY_Y = -14f

    val LIVES_START = 3
    val LIFE_WIDTH = 40f
    val LIFE_HEIGHT = 40f
    val LIFE_SPACING = 10f

    val HUD_WIDTH = 1280f // world unit, 1:1 pixel per unit
    val HUD_HEIGHT = 780f

    val COIN_SCORE = 20
}

