package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import ktx.assets.async.AssetStorage
import ktx.scene2d.KTable
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.image

class MatchBox(
    val hijaiyah: Hijaiyah,
    private val state: State,
    private val assets: AssetStorage,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin),
    // TODO drag and drop mechanics
    KTable {
    private val rightCircle: Image
    val box: HijaiyahBox
    private val leftCircle: Image

    init {
        this@MatchBox.leftCircle = image(Drawables.CIRCLE_ORANGE.drawable) {
            it.padRight(-10.0f)
            isVisible = this@MatchBox.state == State.RIGHT
        }
        this@MatchBox.box = hijaiyahBox(
            hijaiyah,
            HijaiyahBox.Size.SMALL,
            assets
        ) {
            toBack()
        }
        this@MatchBox.rightCircle = image(Drawables.CIRCLE_GREEN.drawable) {
            it.padLeft(-10.0f)
            isVisible = this@MatchBox.state == State.LEFT
        }
    }

    enum class State {
        LEFT,
        RIGHT
    }
}
