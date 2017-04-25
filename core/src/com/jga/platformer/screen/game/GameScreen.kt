package com.jga.platformer.screen.game

import com.badlogic.gdx.assets.AssetManager
import com.jga.platformer.SimplePlatformerGame
import com.jga.platformer.screen.game.world.GameController
import com.jga.platformer.screen.game.world.GameRenderer
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.screen.ScreenBaseAdapter


class GameScreen(val game: SimplePlatformerGame) : ScreenBaseAdapter() {

    val assetManager: AssetManager = game.assetManager

    private lateinit var gameWorld: GameWorld
    private lateinit var renderer: GameRenderer
    private lateinit var controller: GameController

    override fun show() {
        gameWorld = GameWorld()
        renderer = GameRenderer(gameWorld, game.batch, assetManager)
        controller = GameController(gameWorld, renderer)
    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}