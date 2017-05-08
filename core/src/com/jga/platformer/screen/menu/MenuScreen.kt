package com.jga.platformer.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jga.platformer.assets.AssetDescriptors
import com.jga.platformer.assets.RegionNames
import com.jga.platformer.screen.game.GameScreen
import com.jga.util.GdxUtils
import com.jga.util.game.GameBase
import com.jga.util.screen.ScreenBaseAdapter


class MenuScreen(val game: GameBase) : ScreenBaseAdapter() {

    private val assetManager = game.assetManager

    private val viewportManager = game.viewportManager
    private val stage = Stage(viewportManager.getHudViewport(), game.batch)
    val skin = assetManager[AssetDescriptors.SKIN]

    override fun show() {

        val playButton = TextButton("PLAY", skin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                play()
            }
        })

        val quitButton = TextButton("QUIT", skin)
        quitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                quit()
            }
        })

        // create root table
        val table = Table(skin).apply {
            defaults().space(20f)
            setBackground(RegionNames.MENU_BACKGROUND)
            add(playButton).row()
            add(quitButton).row()
            center()
            setFillParent(true)
            pack()
        }

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        viewportManager.applyHud()

        GdxUtils.clearScreen()
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewportManager.resize(width, height)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        Gdx.input.inputProcessor = null
        stage.dispose()
    }

    override fun getInputProcessor(): InputProcessor {
        // used with screen transition
        return stage
    }

    private fun play() {
        game.setScreen(GameScreen(game))
    }

    private fun quit() {
        Gdx.app.exit()
    }
}