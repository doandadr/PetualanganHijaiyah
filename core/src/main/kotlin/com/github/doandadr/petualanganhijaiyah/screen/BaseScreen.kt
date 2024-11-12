package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.doandadr.petualanganhijaiyah.Main
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage

abstract class BaseScreen(
    val game: Main,
    val gameViewport: Viewport = game.gameViewport,
    val uiViewport: Viewport = game.uiViewport,
    val assets: AssetStorage = game.assets,
    val stage: Stage = game.stage,
    val batch: Batch = game.batch
): KtxScreen {

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }
}
