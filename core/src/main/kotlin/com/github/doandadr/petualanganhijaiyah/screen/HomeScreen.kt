package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import ktx.actors.stage
import ktx.app.clearScreen
import ktx.graphics.use
import ktx.log.logger

private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : Screen(game) {

    override fun show() {
        LOG.debug { "Home Screen is shown" }
    }

    override fun render(delta: Float) {
        val atlas = assets[TextureAtlasAsset.DRAWABLE.descriptor]
        val pic = atlas.findRegion("board")
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        game.batch.use {
//            it.draw(image, 100f, 160f)
            it.draw(pic, 9f, 16f)
        }
    }
}
