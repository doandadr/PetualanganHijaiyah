package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import ktx.scene2d.KGroup
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor


class StarWidget(
    val skin: Skin,
    val star1: Image = Image(skin.getDrawable(Drawables.ICON_STARGREY_MEDIUM.drawable)),
    val star2: Image = Image(skin.getDrawable(Drawables.ICON_STARGREY_LARGE.drawable)),
    val star3: Image = Image(skin.getDrawable(Drawables.ICON_STARGREY_MEDIUM.drawable)),
):WidgetGroup(star1, star2, star3), KGroup {
    init {
        isTransform = true
        setInitPosition()
        setState(StarState.ZERO)
        setOrigin(Align.center)
        touchable = Touchable.disabled
    }

    private fun setInitPosition() {
        val bumpY = 20f
        val gapX = -10f
        star2.setPosition(star1.width + gapX, bumpY)
        star3.setPosition(star1.width + star2.width + 2 * gapX, star1.y)
    }


    fun setState(state: StarState) {
        if (state == StarState.HIDDEN) {
            isVisible = false
            return
        }
        isVisible = true

        when (state) {
            StarState.ZERO -> {
                star1.setDrawable(skin, Drawables.ICON_STARGREY_MEDIUM.drawable)
                star2.setDrawable(skin, Drawables.ICON_STARGREY_LARGE.drawable)
                star3.setDrawable(skin, Drawables.ICON_STARGREY_MEDIUM.drawable)
            }
            StarState.ONE -> {
                star1.setDrawable(skin, Drawables.ICON_STAR_MEDIUM.drawable)
                star2.setDrawable(skin, Drawables.ICON_STARGREY_LARGE.drawable)
                star3.setDrawable(skin, Drawables.ICON_STARGREY_MEDIUM.drawable)
            }
            StarState.TWO -> {
                star1.setDrawable(skin, Drawables.ICON_STAR_MEDIUM.drawable)
                star2.setDrawable(skin, Drawables.ICON_STAR_LARGE.drawable)
                star3.setDrawable(skin, Drawables.ICON_STARGREY_MEDIUM.drawable)
            }
            StarState.THREE -> {
                star1.setDrawable(skin, Drawables.ICON_STAR_MEDIUM.drawable)
                star2.setDrawable(skin, Drawables.ICON_STAR_LARGE.drawable)
                star3.setDrawable(skin, Drawables.ICON_STAR_MEDIUM.drawable)
            }
            else -> {}
        }
    }

    enum class StarState {
        HIDDEN,
        ZERO,
        ONE,
        TWO,
        THREE
    }
}


inline fun <S> KWidget<S>.starWidget(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: StarWidget.(S) -> Unit = {}
) = actor(
    StarWidget(
        skin,
    ), init
)
