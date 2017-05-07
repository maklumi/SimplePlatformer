package com.jga.util.viewport

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class ViewportManager(val viewportConfig: ViewportConfig) {

    private val hudCamera = OrthographicCamera()
    private val worldCamera = OrthographicCamera()

    private val hudViewport: Viewport
    private val worldViewport: Viewport

    init {
        hudViewport = FitViewport(viewportConfig.worldWidth, viewportConfig.worldHeight, hudCamera)
        hudViewport.apply()

        worldViewport = FitViewport(viewportConfig.worldWidth, viewportConfig.worldHeight, worldCamera)
        worldViewport.apply()
    }

    fun resize(screenWidth: Int, screenHeight: Int) {
        hudViewport.update(screenWidth, screenHeight, true)
        worldViewport.update(screenWidth, screenHeight, true)
    }

    fun applyHud() {
        hudViewport.apply()
    }

    fun getHudCombined(): Matrix4 {
        return hudCamera.combined
    }

    fun getHudCamera(): OrthographicCamera {
        return hudCamera
    }

    fun getHudViewport(): Viewport {
        return hudViewport
    }

    fun applyWorld() {
        worldViewport.apply()
    }

    fun getWorldCombined(): Matrix4 {
        return worldCamera.combined
    }

    fun getWorldCamera(): OrthographicCamera {
        return worldCamera
    }

    fun getWorldViewport(): Viewport {
        return worldViewport
    }

    fun debugPixelsPerUnit() {
        ViewportUtils.debugPixelsPerUnit(worldViewport)
        ViewportUtils.debugPixelsPerUnit(hudViewport)
    }

    fun getWorldCameraPosition(): Vector2 {
        return Vector2(worldCamera.position.x, worldCamera.position.y)
    }

    fun screenToWorld(screenCoordinates: Vector2): Vector2 {
        return worldViewport.unproject(screenCoordinates)
    }

    fun drawWorldGrid(shapeRenderer: ShapeRenderer) {
        ViewportUtils.drawGrid(worldViewport, shapeRenderer)
    }
}
