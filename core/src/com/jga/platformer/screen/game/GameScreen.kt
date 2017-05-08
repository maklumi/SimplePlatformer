package com.jga.platformer.screen.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.SimplePlatformerGame
import com.jga.platformer.common.EntityFactory
import com.jga.platformer.input.PlayerInputController
import com.jga.platformer.screen.game.world.GameController
import com.jga.platformer.screen.game.world.GameRenderer
import com.jga.platformer.screen.game.world.GameWorld
import com.jga.platformer.screen.menu.MenuScreen
import com.jga.util.game.GameBase
import com.jga.util.screen.ScreenBaseAdapter


class GameScreen(val game: GameBase) : ScreenBaseAdapter() {

    val assetManager: AssetManager = game.assetManager
    private val levelController = (game as SimplePlatformerGame).levelController
    private lateinit var gameWorld: GameWorld
    private lateinit var renderer: GameRenderer
    private lateinit var controller: GameController
    private lateinit var playerInputController: PlayerInputController
    private val factory = EntityFactory(assetManager)

    private val log = Logger(GameScreen::class.java.simpleName, Logger.DEBUG)

    override fun show() {
        levelController.loadRandomLevel()

        gameWorld = GameWorld()
        setupLevel()
        renderer = GameRenderer(gameWorld, game.batch, assetManager, game.viewportManager)
        renderer.setMap(levelController.getCurrentMap())
        controller = GameController(gameWorld, renderer)
        playerInputController = PlayerInputController(gameWorld)
    }

    override fun render(delta: Float) {
        playerInputController.update(delta)
        controller.update(delta)
        renderer.update(delta)

        if (gameWorld.isLevelComplete) {
            levelController.loadRandomLevel()
            setupLevel()
            renderer.setMap(levelController.getCurrentMap())
            gameWorld.playing()
        }

        if (gameWorld.isGameOver) {
            game.setScreen(MenuScreen(game))
        }
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

    private fun setupLevel() {
        factory.setupGameWorld(gameWorld, levelController.getCurrentMap())
    }
}