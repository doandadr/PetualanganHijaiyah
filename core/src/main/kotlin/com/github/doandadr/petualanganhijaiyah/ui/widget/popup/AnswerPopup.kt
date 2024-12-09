package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import ktx.preferences.get
import ktx.scene2d.*


class AnswerPopup(
    state: State,
    preferences: Preferences,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private val character = preferences[PrefKey.PLAYER.key, PlayerModel()].character
    private val charImg = if (character == "girl") Drawables.GIRL_SELECT.drawable else Drawables.BOY_SELECT.drawable
    private val bannerText = if (state==State.CORRECT) "BENAR" else "SALAH"
    private val bannerStyle = if (state == State.CORRECT) Labels.BANNER_GREEN.style else Labels.BANNER_RED.style

    init {
        image(this@AnswerPopup.charImg)
        row()
        label(this@AnswerPopup.bannerText, this@AnswerPopup.bannerStyle) {
            setAlignment(Align.center)
            it.padTop(-40f)
        }
    }

    enum class State {
        CORRECT,
        INCORRECT
    }
}

inline fun <S> KWidget<S>.answerPopup(
    state: AnswerPopup.State,
    preferences: Preferences,
    init: AnswerPopup.(S) -> Unit = {}
) = actor(
    AnswerPopup(
        state,
        preferences,
    ), init
)