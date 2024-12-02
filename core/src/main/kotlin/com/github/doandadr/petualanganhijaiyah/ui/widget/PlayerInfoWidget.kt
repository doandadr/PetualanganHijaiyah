package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import ktx.scene2d.*

class PlayerInfoWidget(
    private val playerName: String,
    maxHeart: Int,
    playerGender: String,
    skin: Skin = Scene2DSkin.defaultSkin,
): Table(skin), KTable {
    private val nameSign: Label
    private val portrait: Image
    private val heartView = mutableListOf<Image>()

    init {
        table {
            this@PlayerInfoWidget.nameSign = label(this@PlayerInfoWidget.playerName, Labels.SIGN_NAME.style) {
                setAlignment(Align.center)
                setFontScale(5f/this@PlayerInfoWidget.playerName.length)
                setScale(SCALE_BTN_SMALL)

                it.padRight(-50f)
            }

            row()
            table {
                for (i in  0 until maxHeart) {
                    this@PlayerInfoWidget.heartView += image(skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable)) {
                        setOrigin(Align.center)
                        it.prefSize(40f)
                    }
                }
                it.padRight(10f)
            }
        }
        this@PlayerInfoWidget.portrait = image {
            it.prefSize(120f)
        }

        setPlayerPortrait(playerGender)
        setHeartCount(maxHeart)
    }

    fun setHeartCount(hearts: Int) {
        heartView.forEach {
            it.drawable = skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable)
        }
        heartView.take(hearts).forEach { it.drawable = skin.getDrawable(Drawables.ICON_HEART_FULL.drawable) }
    }

    private fun setPlayerPortrait(playerGender: String) {
        if (playerGender == "male") {
            portrait.drawable = skin.getDrawable(Drawables.BOY_SELECT.drawable)
        } else {
            portrait.drawable = skin.getDrawable(Drawables.GIRL_SELECT.drawable)
        }
    }
}

inline fun <S> KWidget<S>.playerInfoWidget(
    playerName: String,
    maxHeart: Int,
    playerGender: String,
    init: PlayerInfoWidget.(S) -> Unit = {}
) = actor(
    PlayerInfoWidget(
        playerName,
        maxHeart,
        playerGender
    ), init
)
