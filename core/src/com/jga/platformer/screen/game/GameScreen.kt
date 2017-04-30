package com.jga.platformer.screen.game

import com.badlogic.gdx.assets.AssetManager
import com.jga.platformer.common.EntityFactory
import com.jga.platformer.input.PlayerInputController
import com.jga.platformer.screen.game.world.GameController
import com.jga.platformer.screen.game.world.GameRenderer
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.util.game.GameBase
import com.jga.util.screen.ScreenBaseAdapter


class GameScreen(val game: GameBase) : ScreenBaseAdapter() {

    val assetManager: AssetManager = game.assetManager

    private lateinit var gameWorld: GameWorld
    private lateinit var renderer: GameRenderer
    private lateinit var controller: GameController
    private lateinit var playerInputController: PlayerInputController
    private val factory = EntityFactory(assetManager)

    override fun show() {
        gameWorld = factory.createGameWorld()
        renderer = GameRenderer(gameWorld, game.batch, assetManager)
        controller = GameController(gameWorld, renderer)
        playerInputController = PlayerInputController(gameWorld)
    }

    override fun render(delta: Float) {
        playerInputController.update(delta)
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