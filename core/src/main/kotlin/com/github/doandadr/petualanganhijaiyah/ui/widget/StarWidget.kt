package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import ktx.scene2d.KGroup
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor

enum class StarState {
    HIDDEN,
    ZERO,
    ONE,
    TWO,
    THREE
}

class Stars(
    val skin: Skin,
    val star1: Image = Image(skin.getDrawable("star-grey")),
    val star2: Image = Image(skin.getDrawable("star-grey")),
    val star3: Image = Image(skin.getDrawable("star-grey")),
):WidgetGroup(star1, star2, star3), KGroup {
    init {
        isTransform = true
        setInitPosition()
        setState(StarState.ZERO)
        setOrigin(Align.center)
        setScale(0.8f)
        star2.setOrigin(Align.center)
        star2.setScale(1.1f)
    }

    private fun setInitPosition() {
        val bumpY = 30f
        val gapX = -10f
        star2.setPosition(star1.width + gapX, bumpY)
        star3.setPosition(star1.width + star2.width + 2 * gapX, star1.y)
    }

    fun setState(state: StarState) {
        if (state != StarState.HIDDEN) isVisible = true
        when (state) {
            StarState.HIDDEN -> {
                if (this.isVisible)
                this.isVisible = false
            }
            StarState.ZERO -> {
                listOf(star1, star2, star3).forEach { star ->
                    star.setDrawable(skin, "star-grey")
                }
            }
            StarState.ONE -> {
                star1.setDrawable(skin, "star")
                star2.setDrawable(skin, "star-grey")
                star3.setDrawable(skin, "star-grey")
            }
            StarState.TWO -> {
                star1.setDrawable(skin, "star")
                star2.setDrawable(skin, "star")
                star3.setDrawable(skin, "star-grey")
            }
            StarState.THREE -> {
                star1.setDrawable(skin, "star")
                star2.setDrawable(skin, "star")
                star3.setDrawable(skin, "star")
            }
        }
    }
}


inline fun <S> KWidget<S>.stars(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: Stars.(S) -> Unit = {}
) = actor(
    Stars(
        skin,
    ), init
)
