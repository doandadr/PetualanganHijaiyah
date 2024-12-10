package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.CheckBoxes
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.screen.HomeScreen.PopupState
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_SMALL
import ktx.actors.onChangeEvent
import ktx.log.logger
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*

private val log = logger<SettingsPopup>()

class SettingsPopup(
    private val preferences: Preferences,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val confirmButton: TextButton
    private val soundSlider: Slider
    private val soundToggle: CheckBox
    private val musicSlider: Slider
    private val musicToggle: CheckBox

    var soundVolume: Float = preferences[PrefKey.SOUND_VOLUME.key, 1f]
    var musicVolume: Float = preferences[PrefKey.MUSIC_VOLUME.key, 1f]

    init {
        label("PENGATURAN", Labels.BOARD.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_SMALL)
        }

        row()
        table {
            background = skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable)
            align(Align.top)

            this@SettingsPopup.soundToggle = checkBox("", CheckBoxes.SOUND.style) {
                isChecked = this@SettingsPopup.soundVolume > 0f
                it.padTop(OPTION_PADDING).prefSize(TOGGLE_SIZE)
            }
            this@SettingsPopup.soundSlider = slider {
                value = this@SettingsPopup.soundVolume
                it.padTop(OPTION_PADDING).prefWidth(SLIDER_WIDTH)
            }

            row()
            this@SettingsPopup.musicToggle = checkBox("", CheckBoxes.MUSIC.style) {
                isChecked = this@SettingsPopup.musicVolume > 0f
                it.padTop(OPTION_PADDING).prefSize(TOGGLE_SIZE)
            }
            this@SettingsPopup.musicSlider = slider {
                value = this@SettingsPopup.musicVolume
                it.padTop(OPTION_PADDING).prefWidth(SLIDER_WIDTH)
            }

            row()
            add().expandY().colspan(2)

            row()
            this@SettingsPopup.confirmButton = textButton("OK", TextButtons.GREEN_LARGE.style) {
                it.padBottom(PADDING_INNER_SCREEN).colspan(2)
            }
            it.spaceTop(20f).spaceTop(40f).prefWidth(500f).prefHeight(600f)
        }

        setupListeners()
    }

    private fun setupListeners() {
        soundToggle.onChangeEvent {
            audioService.soundVolume = if (isChecked) soundVolume else 0f
            log.debug { "Sound is on: $isChecked at volume ${audioService.soundVolume}" }
        }
        soundSlider.onChangeEvent {
            soundVolume = value
            soundToggle.isChecked = value > 0f
            audioService.soundVolume = soundVolume
            log.debug { "Sound volume changed to $value" }
        }
        musicToggle.onChangeEvent {
            audioService.musicVolume = if (isChecked) musicVolume else 0f
            log.debug { "Music is on: $isChecked at volume ${audioService.musicVolume}" }
        }
        musicSlider.onChangeEvent {
            musicVolume = value
            musicToggle.isChecked = value > 0f
            audioService.musicVolume = musicVolume
            log.debug { "Music volume changed to $value" }
        }
        confirmButton.onChangeEvent {
            log.debug { "Confirm button pressed" }
            preferences.flush {
                preferences[PrefKey.MUSIC_VOLUME.key] = musicVolume
                preferences[PrefKey.SOUND_VOLUME.key] = soundVolume
            }
            gameEventManager.dispatchSetHomePopupStateEvent(PopupState.NONE)
        }
    }

    companion object {
        private const val TOGGLE_SIZE = 100f
        private const val SLIDER_WIDTH = 300f
        private const val OPTION_PADDING = 50f
    }
}

inline fun <S> KWidget<S>.settingsPopup(
    preferences: Preferences,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: SettingsPopup.(S) -> Unit = {}
) = actor(
    SettingsPopup(
        preferences,
        audioService,
        gameEventManager,
        skin,
    ), init
)
