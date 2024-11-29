package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import ktx.scene2d.*

class NameChangePopup(
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val nameField: TextField
    val confirmButton: TextButton

    init {
        isVisible = false

        setFillParent(true)
        label("UBAH NAMA", Labels.BOARD.style).setAlignment(Align.center)
        row()
        add().padBottom(50f).row()
        nameField = textField("Ubah Nama"){
            it.prefWidth(480f).prefHeight(80f)
        }
        row()
        add().padBottom(50f).row()
        confirmButton = textButton("OK", TextButtons.CONFIRM.style)
    }
}

inline fun <S> KWidget<S>.nameChangePopup(
    init: NameChangePopup.(S) -> Unit = {}
) = actor(
    NameChangePopup(), init
)
