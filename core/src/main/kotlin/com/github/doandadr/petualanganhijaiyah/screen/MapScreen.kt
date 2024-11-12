package com.github.doandadr.petualanganhijaiyah.screen

import com.github.doandadr.petualanganhijaiyah.Main
import ktx.log.logger

private val LOG = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        LOG.debug { "Map Screen is shown" }
    }

    override fun render(delta: Float) {

    }
}
