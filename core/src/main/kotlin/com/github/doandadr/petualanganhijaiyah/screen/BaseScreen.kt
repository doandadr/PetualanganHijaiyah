package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.compression.lzma.Base
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventListener
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.log.logger
import java.lang.System.currentTimeMillis
import kotlin.math.log

private val log = logger<BaseScreen>()

abstract class BaseScreen(
    val game: Main,
    val uiViewport: Viewport = game.uiViewport,
    val assets: AssetStorage = game.assets,
    val audioService: AudioService = game.audioService,
    val stage: Stage = game.stage,
    val gameEventManager: GameEventManager = game.gameEventManager,
    val preferences: Preferences = game.preferences,
    ): KtxScreen, GameEventListener {

    override fun show() {
        // TODO register event for screen
        gameEventManager.addGameEventListener(this)
    }

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)
    }

    override fun hide() {
        log.debug { "Hide ${this::class.simpleName}" }
        stage.clear()
        audioService.stop()
        gameEventManager.removeGameEventListener(this)
    }
}
