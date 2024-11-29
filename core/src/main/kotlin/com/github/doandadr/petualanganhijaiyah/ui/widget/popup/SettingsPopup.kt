package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.util.centerX
import ktx.actors.onChange
import ktx.scene2d.*

class SettingsPopup(
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val volumeSlider: Slider
    val confirmButton: Button

    init {
        setFillParent(true)
        container {
            isTransform = true
            setOrigin(Align.center)
            setScale(SCALE_BTN_MEDIUM)
            setPosition(centerX(prefWidth), 0f)
            label("PENGATURAN", Labels.BOARD.style).setAlignment(Align.center)
        }
        row()
        table {
            background = skin.getDrawable(Drawables.WINDOW_TRANS.drawable)
            align(Align.top)
            image(skin.getDrawable(Drawables.ICON_AUDIO.drawable))
            this@SettingsPopup.volumeSlider = slider(
                0f, 100f, 1f
            ) {
                it.prefWidth(300f).spaceLeft(10f).row()
                onChange {
                }
            }
            add().expand().row()
            this@SettingsPopup.confirmButton = textButton("OK", TextButtons.CONFIRM.style) {
                it.colspan(2).align(Align.bottom)

            }
            it.spaceTop(20f).padTop(50f).prefWidth(500f).prefHeight(600f)
        }
        isVisible = false
    }
}

inline fun <S> KWidget<S>.settingsPopup(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: SettingsPopup.(S) -> Unit = {}
) = actor(
    SettingsPopup(
        skin,
    ), init
)
