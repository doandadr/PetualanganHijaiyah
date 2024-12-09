package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.Colors
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.screen.HomeScreen.PopupState
import ktx.actors.onChangeEvent
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*

class CharacterSelectPopup(
    private val preferences: Preferences,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    private val girlButton: Button
    private val boyButton: Button

    private val player = preferences[PrefKey.PLAYER.key, PlayerModel()]

    init {
        verticalGroup {
            space(50f)
            label("PILIH KARAKTER", Labels.BOARD.style).setAlignment(Align.center)
            this@CharacterSelectPopup.girlButton = button(Buttons.GIRL_SELECT.style) {
                color = skin.getColor(if (this@CharacterSelectPopup.player.character == GIRL) Colors.LIGHT_GREEN.color else Colors.WHITE.color)
            }
            this@CharacterSelectPopup.boyButton = button(Buttons.BOY_SELECT.style) {
                color = skin.getColor(if (this@CharacterSelectPopup.player.character == BOY) Colors.LIGHT_GREEN.color else Colors.WHITE.color)
            }
        }

        setupListeners()
    }

    private fun setupListeners() {
        girlButton.onChangeEvent {
            preferences.flush {
                preferences[PrefKey.PLAYER.key] = player.apply { character = GIRL }
            }
            audioService.play(SoundAsset.CLICK_BUTTON)
            gameEventManager.dispatchSetHomePopupStateEvent(PopupState.NONE)
        }
        boyButton.onChangeEvent {
            preferences.flush {
                preferences[PrefKey.PLAYER.key] = player.apply { character = BOY }
            }
            audioService.play(SoundAsset.CLICK_BUTTON)
            gameEventManager.dispatchSetHomePopupStateEvent(PopupState.NONE)
        }
    }

    companion object {
        private const val GIRL = "girl"
        private const val BOY = "boy"
    }
}

inline fun <S> KWidget<S>.characterSelectPopup(
    preferences: Preferences,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    init: CharacterSelectPopup.(S) -> Unit = {}
) = actor(
    CharacterSelectPopup(
        preferences,
        audioService,
        gameEventManager,
    ), init
)
