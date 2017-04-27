package com.jga.platformer.screen.game.world

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.jga.platformer.assets.AssetDescriptors
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

        // render debug
        renderDebug()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true) // true = center camera
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

    private fun drawDebug() {
        shapeRenderer.color = Color.GOLD

        ShapeRendererUtils.entities(shapeRenderer, gameWorld.waterHazards)

        shapeRenderer.color = Color.OLIVE
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.platforms)

        shapeRenderer.color = Color.CYAN
        ShapeRendererUtils.entity(shapeRenderer, gameWorld.player)
        ShapeRendererUtils.entities(shapeRenderer, gameWorld.coins)

    }
}