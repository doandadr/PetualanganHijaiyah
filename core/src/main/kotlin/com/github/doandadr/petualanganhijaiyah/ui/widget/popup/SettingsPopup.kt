package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.CheckBoxes
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_SMALL
import ktx.actors.onChangeEvent
import ktx.actors.onTouchEvent
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<SettingsPopup>()

class SettingsPopup(
    val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val confirmButton: TextButton
    val volumeSlider: Slider
    val volumeToggle: CheckBox

    init {
        debugAll()
        label("PENGATURAN", Labels.BOARD.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_SMALL)
        }
        row()
        table {
            background = skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable)
            align(Align.top)

            this@SettingsPopup.volumeToggle = checkBox("", CheckBoxes.AUDIO.style) {
                it.padTop(50f).prefSize(100f)
            }
            this@SettingsPopup.volumeSlider = slider {

                it.padTop(50.0f).prefWidth(300.0f)
            }

            row()
            add().expandY().colspan(1)
            row()

            this@SettingsPopup.confirmButton = textButton("OK", TextButtons.GREEN_LARGE.style) {
                it.padBottom(30f).colspan(2)
            }

            it.spaceTop(20f).spaceTop(40f).prefWidth(500f).prefHeight(600f)
        }
        volumeToggle.onChangeEvent {
            audioService.enabled = isChecked
        }
        volumeSlider.onChangeEvent {
            // TODO change volume with $value
        }
        volumeSlider.onTouchEvent(
            onDown = { _ ->
            },
            onUp = {
                // TODO set volume value preferences
            },
        )
        onChangeEvent {
            // TODO confirm go back to home screen
        }
        isVisible = false
    }
}

fun doNothing() {

}

inline fun <S> KWidget<S>.settingsPopup(
    audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: SettingsPopup.(S) -> Unit = {}
) = actor(
    SettingsPopup(
        audioService,
        skin,
    ), init
)
