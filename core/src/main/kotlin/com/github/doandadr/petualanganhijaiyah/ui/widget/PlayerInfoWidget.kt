package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import ktx.preferences.get
import ktx.scene2d.*
import kotlin.math.max

class PlayerInfoWidget(
    private val preferences: Preferences,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
): Table(skin), KTable {
    private val nameSign: Label
    private val portrait: Image
    private val hearts = mutableListOf<Image>()
    private val heartsView: Table
    private var health: Int = 5

    private val player = preferences[PrefKey.PLAYER.key, PlayerModel()]

    init {
        table {
            this@PlayerInfoWidget.nameSign = label(this@PlayerInfoWidget.player.name, Labels.SIGN_NAME.style) {
                setAlignment(Align.center)
                setFontScale(5f / max(3, this@PlayerInfoWidget.player.name.length))
                setScale(SCALE_BTN_SMALL)
                it.padRight(-50f)
            }

            row()
            this@PlayerInfoWidget.heartsView = table {
                it.padRight(10f)
            }
        }
        this@PlayerInfoWidget.portrait = image {
            it.prefSize(120f)
        }
    }

    fun loadWidget(maxHearts: Int) {
        health = maxHearts
        heartsView.clear()
        hearts.clear()
        for (i in  0 until maxHearts) {
            val heart = Image(skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable))
            heart.setOrigin(Align.center)
            hearts.add(heart)
            heartsView.add(hearts[i]).prefSize(40f)
        }
        setPlayerPortrait(player.character)
        setHeartCount(maxHearts)
    }

    private fun setHeartCount(hearts: Int) {
        this.hearts.forEach {
            it.drawable = skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable)
        }
        this.hearts.take(hearts).forEach { it.drawable = skin.getDrawable(Drawables.ICON_HEART_FULL.drawable) }
    }

    fun loseHealth() {
        health -= 1
        setHeartCount(health)
        if (health <= 0) {
            gameEventManager.dispatchLevelFailEvent()
        }
    }

    private fun setPlayerPortrait(playerCharacter: String) {
        if (playerCharacter == "boy") {
            portrait.drawable = skin.getDrawable(Drawables.BOY_SELECT.drawable)
        } else {
            portrait.drawable = skin.getDrawable(Drawables.GIRL_SELECT.drawable)
        }
    }
}

inline fun <S> KWidget<S>.playerInfoWidget(
    preferences: Preferences,
    gameEventManager: GameEventManager,
    init: PlayerInfoWidget.(S) -> Unit = {}
) = actor(
    PlayerInfoWidget(
        preferences,
        gameEventManager,
    ), init
)
