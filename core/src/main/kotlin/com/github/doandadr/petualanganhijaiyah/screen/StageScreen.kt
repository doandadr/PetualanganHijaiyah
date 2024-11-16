package com.github.doandadr.petualanganhijaiyah.screen

import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.scene2d.actors

class StageScreen(game: Main): BaseScreen(game) {

    override fun show() {
        super.show()
    }

    private fun setupUI() {
        val bgStage = assets[TextureAsset.STAGE.descriptor]

        stage.actors {

        }
    }
}
