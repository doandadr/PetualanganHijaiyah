package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage

abstract class BaseScreen(
    val game: Main,
    val uiViewport: Viewport = game.uiViewport,
    val assets: AssetStorage = game.assets,
    val audioService: AudioService = game.audioService,
    val stage: Stage = game.stage,
): KtxScreen {

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)
    }
}
