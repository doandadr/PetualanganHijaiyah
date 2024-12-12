package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.screen.HomeScreen.PopupState
import ktx.actors.onChangeEvent
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*

class NameChangePopup(
    private val preferences: Preferences,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val nameField: TextField
    val confirmButton: TextButton

    init {
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

        setupListeners()
        setupTutorial()
    }

    private fun setupTutorial() {
        Gdx.app.postRunnable {
            gameEventManager.dispatchShowTutorialEvent(nameField, TutorialType.NAME_CHANGE)
        }
    }

    private fun setupListeners() {
        confirmButton.onChangeEvent {
            val player = preferences[PrefKey.PLAYER.key, PlayerModel()]
            player.name = nameField.text
            preferences.flush {
                preferences[PrefKey.PLAYER.key] = player
            }
            gameEventManager.dispatchPlayerChangedEvent(player)
            gameEventManager.dispatchSetHomePopupStateEvent(PopupState.CHARACTER)
        }
    }
}

inline fun <S> KWidget<S>.nameChangePopup(
    preferences: Preferences,
    gameEventManager: GameEventManager,
    init: NameChangePopup.(S) -> Unit = {}
) = actor(
    NameChangePopup(
        preferences,
        gameEventManager,
    ), init
)
