package com.jga.platformer.common


enum class GameState {
    PLAYING,
    LEVEL_COMPLETE,
    GAME_OVER;

    val isPlaying: Boolean
        get() = this == PLAYING

    val isLevelComplete: Boolean
        get() = this == LEVEL_COMPLETE

    val isGameOver: Boolean
        get() = this == GAME_OVER
}