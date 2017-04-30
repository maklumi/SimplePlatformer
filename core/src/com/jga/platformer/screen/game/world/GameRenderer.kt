package com.jga.platformer.screen.game.world

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.RegionNames
import com.jga.platformer.config.GameConfig
import com.jga.platformer.config.GameConfig.UNIT_SCALE
import com.jga.platformer.config.GameConfig.WORLD_CENTER_X
import com.jga.platformer.config.GameConfig.WORLD_CENTER_Y
import com.jga.platformer.config.GameConfig.WORLD_HEIGHT
import com.jga.platformer.config.GameConfig.WORLD_WIDTH
import com.jga.util.GdxUtils
import com.jga.util.debug.DebugCameraController
import com.jga.util.debug.ShapeRendererUtils
import com.jga.util.viewport.ViewportUtils


class GameRenderer(val gameWorld: GameWorld, batch: SpriteBatch, assetManager: AssetManager) : Disposable {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val shapeRenderer = ShapeRenderer()
    private val debugCameraController = DebugCameraController().apply { setStartPosition(WORLD_CENTER_X, WORLD_CENTER_Y) }
    private val map = assetManager[AssetDescriptors.LEVEL_01]
    private val mapRenderer = OrthogonalTiledMapRenderer(map, UNIT_SCALE, batch)

    private val gamePlayAtlas = assetManager[AssetDescriptors.GAME_PLAY]

    private val coinRegion = gamePlayAtlas.findRegion(RegionNames.COIN)
    private val fallingRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER_FALLING)
    private val jumpingRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER_JUMPING)

    private val batch = SpriteBatch()

    private val PADDING = 40f
    private val WHITE_HALF_TRANSPARENT = Color(1f, 1f, 1f, 0.5f)
    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT)
    private val layout = GlyphLayout()
    private val lifeRegion = gamePlayAtlas.findRegion(RegionNames.LIFE)
    private val font = assetManager[AssetDescriptors.FONT]

    fun update(delta: Float) {
        // handle debug camera input
        debugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        // clear screen
        GdxUtils.clearScreen()

        // render map
        mapRenderer.apply {
            setView(camera) // internally sets project matrix, important to call
            render() // internally calls begin()/end() from SpriteBatch
        }

        renderGamePlay()

        // render debug
//        renderDebug()

        renderHud()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true) // true = center camera
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelsPerUnit(viewport)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    override fun dispose() {
        shapeRenderer.dispose()
    }

    private fun renderDebug() {
        if (gameWorld.isDrawGrid) ViewportUtils.drawGrid(viewport, shapeRenderer)

        if (!gameWorld.isDrawDebug) return

        val oldColor = shapeRenderer.color.cpy()

        viewport.apply()
        shapeRenderer.apply {
            projectionMatrix = camera.combined
            begin(ShapeRenderer.ShapeType.Line)
        }

        drawDebug()

        shapeRenderer.apply {
            end()
            color = oldColor
        }
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()

        drawGamePlay()

        batch.end()
    }

    private fun drawGamePlay() {
        // coins
        val coins = gameWorld.coins
        for (coin in coins) {
            batch.draw(coinRegion, coin.x, coin.y, coin.width, coin.height)
        }

        // player
        val player = gameWorld.player
        var region = jumpingRegion

        if (player.state.isFalling) region = fallingRegion

        val isFlipX = region.isFlipX
        if (player.isfacingRight && isFlipX) region.flip(true, false)
        if (!player.isfacingRight && !isFlipX) region.flip(true, false)

        batch.draw(region, player.x, player.y, player.width, player.height)
    }

    private fun drawDebug() {
        shapeRenderer.color = Color.GOLD

        ShapeRendererUtils.entities(shapeRenderer, gameWorld.waterHazards)

        shapeRenderer.color = Color.OLIVE
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.platforms)

        shapeRenderer.color = Color.CYAN
        ShapeRendererUtils.entity(shapeRenderer, gameWorld.player)
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.coins)

    }

    private fun renderHud() {
        hudViewport.apply()
        val oldColor = batch.color.cpy()
        batch.projectionMatrix = hudViewport.camera.combined
        batch.begin()

        drawHud()

        batch.end()
        batch.color = oldColor
    }

    private fun drawHud() {
        // score
        val scoreString = "Score: ${gameWorld.score}"
        layout.setText(font, scoreString)

        val scoreY = GameConfig.HUD_HEIGHT - layout.height
        font.draw(batch, layout, PADDING, scoreY)

        // lives
        val offsetX = GameConfig.LIVES_START * (GameConfig.LIFE_WIDTH + GameConfig.LIFE_SPACING)
        val offsetY = GameConfig.LIFE_HEIGHT + GameConfig.LIFE_SPACING
        val startX = GameConfig.HUD_WIDTH - offsetX
        val startY = GameConfig.HUD_HEIGHT - offsetY

        for (i in 0..GameConfig.LIVES_START) {
            if (gameWorld.lives <= i) batch.color = WHITE_HALF_TRANSPARENT

            val x = startX + i * (GameConfig.LIFE_WIDTH + GameConfig.LIFE_SPACING)
            batch.draw(lifeRegion, x, startY, GameConfig.LIFE_WIDTH, GameConfig.LIFE_HEIGHT)
        }

    }
}