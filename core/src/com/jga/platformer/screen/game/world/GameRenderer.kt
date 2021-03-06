package com.jga.platformer.screen.game.world

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Logger
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.RegionNames
import com.jga.platformer.config.GameConfig
import com.jga.platformer.config.GameConfig.UNIT_SCALE
import com.jga.platformer.config.GameConfig.WORLD_CENTER_X
import com.jga.platformer.config.GameConfig.WORLD_CENTER_Y
import com.jga.util.GdxUtils
import com.jga.util.debug.DebugCameraController
import com.jga.util.debug.ShapeRendererUtils
import com.jga.util.viewport.ViewportManager
import com.jga.util.viewport.ViewportUtils


class GameRenderer(val gameWorld: GameWorld, val batch: SpriteBatch, assetManager: AssetManager, val viewportManager: ViewportManager) : Disposable {

    private val camera = viewportManager.getWorldCamera()
    private val viewport = viewportManager.getWorldViewport()
    private val shapeRenderer = ShapeRenderer()
    private val debugCameraController = DebugCameraController().apply { setStartPosition(WORLD_CENTER_X, WORLD_CENTER_Y) }
    private var mapRenderer: OrthogonalTiledMapRenderer? = null

    private val gamePlayAtlas = assetManager[AssetDescriptors.GAME_PLAY]

    private val coinRegion = gamePlayAtlas.findRegion(RegionNames.COIN)
    private val fallingRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER_FALLING)
    private val jumpingRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER_JUMPING)

    private val PADDING = 40f
    private val WHITE_HALF_TRANSPARENT = Color(1f, 1f, 1f, 0.5f)
    private val hudViewport = viewportManager.getHudViewport()
    private val layout = GlyphLayout()
    private val lifeRegion = gamePlayAtlas.findRegion(RegionNames.LIFE)
    private val font = assetManager[AssetDescriptors.FONT]
    private val backgroundTexture = assetManager[AssetDescriptors.BACKGROUND]

    private val log = Logger(GameRenderer::class.java.simpleName, Logger.DEBUG)

    fun update(delta: Float) {
        // handle debug camera input
        debugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        // clear screen
        GdxUtils.clearScreen()

        renderBackground()

        // render map
        if (mapRenderer != null) {
            mapRenderer!!.apply {
                setView(camera) // internally sets project matrix, important to call
                render() // internally calls begin()/end() from SpriteBatch
            }
        } else {
            log.debug("Map is not set on GameRenderer")
        }


        renderGamePlay()

        // render debug
        renderDebug()

        renderHud()
    }

    fun resize(width: Int, height: Int) {
        viewportManager.resize(width, height)
        viewportManager.debugPixelsPerUnit()
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 = viewport.unproject(screenCoordinates)

    fun setMap(map: TiledMap) {
        if (mapRenderer == null) mapRenderer = OrthogonalTiledMapRenderer(map, UNIT_SCALE, batch)
        mapRenderer!!.map = map
    }

    override fun dispose() {
        shapeRenderer.dispose()
        mapRenderer?.dispose()
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

    private fun renderBackground() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(backgroundTexture, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT)
        batch.end()
    }

}