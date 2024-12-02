package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import ktx.scene2d.*

class PlayerInfo(
    private val playerName: String,
    private val maxHeart: Int,
    playerGender: String,
    skin: Skin = Scene2DSkin.defaultSkin,
): Table(skin), KTable {
    private val heartView = mutableListOf<Image>()

    init {
        // table
            // label
            // heart info -> HorizontalGroup with n number of hearts
        // image of player, get from user gender
        table {
            label(this@PlayerInfo.playerName, Labels.SIGN_NAME.style) {
                setAlignment(Align.center)
                setFontScale(5f/this@PlayerInfo.playerName.length)

                it.padRight(-50f).growX()
            }

            row()
            table {
                for (i in  0 until this@PlayerInfo.maxHeart) {
                    this@PlayerInfo.heartView += image(skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable)) {
                        setOrigin(Align.center)
                        it.prefSize(40f)
                    }
                }
            }
        }
        image(skin.getDrawable(
            if (playerGender == "male") Drawables.BOY_SELECT.drawable else Drawables.GIRL_SELECT.drawable
        )) {
            it.prefSize(150f)
        }

        setHeartCount(5)
    }
    fun setHeartCount(hearts: Int) {
        heartView.forEach {
            it.drawable = skin.getDrawable(Drawables.ICON_HEART_EMPTY.drawable)
        }
        heartView.take(hearts).forEach { it.drawable = skin.getDrawable(Drawables.ICON_HEART_FULL.drawable) }
    }

    fun setPlayerPortrait() {

    }
}

inline fun <S> KWidget<S>.playerInfo(
    playerName: String,
    maxHeart: Int,
    playerGender: String,
    init: PlayerInfo.(S) -> Unit = {}
) = actor(
    PlayerInfo(
        playerName,
        maxHeart,
        playerGender
    ), init
)
