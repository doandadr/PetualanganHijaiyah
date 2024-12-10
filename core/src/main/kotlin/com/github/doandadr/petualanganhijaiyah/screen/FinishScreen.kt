package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import ktx.log.logger
import ktx.preferences.get
import ktx.scene2d.actors
import ktx.scene2d.image
import ktx.scene2d.table

class FinishScreen(game: Main): BaseScreen(game) {
    // properties
    private lateinit var  player: PlayerModel

    // show
    override fun show() {
        super.show()

        setupData()
        setupUI()
    }

    private fun setupData() {
        player = preferences[PrefKey.PLAYER.key, PlayerModel()]
    }

    private fun setupUI() {
//        val bgFinish = assets[TextureAsset.FINISH.descriptor]

        stage.actors {
            image(/*bgFinish*/) {
                setFillParent(true)
                setScaling(Scaling.fill)
            }
            table {
                // TODO set finish screen layout
            }
        }
    }

    // ui [start bg, popup bubble, etc]
    // render

    companion object {
        private val log = logger<FinishScreen>()
    }
}
