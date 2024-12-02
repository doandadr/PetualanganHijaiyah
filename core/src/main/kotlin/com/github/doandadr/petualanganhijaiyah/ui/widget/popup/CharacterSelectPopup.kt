package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import ktx.scene2d.*

class CharacterSelectPopup(
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val girlButton: Button
    val boyButton: Button

    init {
        verticalGroup {
            space(50f)
            label("KARAKTER", Labels.BOARD.style).setAlignment(Align.center)
            this@CharacterSelectPopup.girlButton = button(Buttons.GIRL_SELECT.style)
            this@CharacterSelectPopup.boyButton = button(Buttons.BOY_SELECT.style)
        }

        isVisible = false
    }
}

inline fun <S> KWidget<S>.characterSelectPopup(
    init: CharacterSelectPopup.(S) -> Unit = {}
) = actor(
    CharacterSelectPopup(), init
)
