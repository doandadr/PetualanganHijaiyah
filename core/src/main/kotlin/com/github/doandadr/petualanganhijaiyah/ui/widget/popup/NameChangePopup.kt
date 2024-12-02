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

        label("Ubah Nama", Labels.BOARD.style) {
            setAlignment(Align.center)
        }
        row()

        this@NameChangePopup.nameField = textField {
            alignment = Align.center
            messageText = "Nama"
            it.spaceTop(50.0f).spaceBottom(50.0f).prefWidth(480.0f)
        }
        row()

        this@NameChangePopup.confirmButton = textButton("OK", TextButtons.GREEN_LARGE.style)
    }
}

inline fun <S> KWidget<S>.nameChangePopup(
    init: NameChangePopup.(S) -> Unit = {}
) = actor(
    NameChangePopup(), init
)
