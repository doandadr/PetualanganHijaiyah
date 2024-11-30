package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import ktx.scene2d.KGroup
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor


class LevelDots(
    val skin: Skin,
    val dot1: Image = Image(skin.getDrawable(Drawables.CIRCLE_GREY.drawable)),
    val dot2: Image = Image(skin.getDrawable(Drawables.CIRCLE_GREY.drawable)),
    val dot3: Image = Image(skin.getDrawable(Drawables.CIRCLE_GREY.drawable)),
    val dot4: Image = Image(skin.getDrawable(Drawables.CIRCLE_GREY.drawable))
) : WidgetGroup(dot1, dot2, dot3 ,dot4), KGroup {
    init {
        setInitPosition()
        setScale(0.9f)
    }

    private fun setInitPosition() {
        val gapY = 70f
        val gapX = 20f
        dot2.setPosition(1 * gapX, 1 * gapY)
        dot3.setPosition(3 * gapX, 1.9f * gapY)
        dot4.setPosition(6 * gapX, 2.7f * gapY)
        dot4.isVisible = false
    }

    fun addCircle() {
        dot4.isVisible = true
    }

    fun flipHorizontal() {
        val gapY = 70f
        val gapX = 20f
        dot2.setPosition(-1 * gapX, 1 * gapY)
        dot3.setPosition(-3 * gapX, 1.9f * gapY)
        dot4.setPosition(-6 * gapX, 2.7f * gapY)
        dot4.isVisible = false
    }

    fun setPositionsDetail(d1x: Float, d2x: Float, d3x: Float, d1y: Float, d2y: Float, d3y: Float) {
        setPosition(d1x, d1y)
        dot2.setPosition(d2x, d2y)
        dot3.setPosition(d3x, d3y)
    }


    fun setState(state: DotState) {
        if (state == DotState.HIDDEN) {
            isVisible = false
            return
        }
        isVisible = true

        when (state) {
            DotState.INACCESSIBLE -> setDrawables(Drawables.CIRCLE_GREY.drawable)
            DotState.AVAILABLE -> setDrawables(Drawables.CIRCLE_GREEN.drawable)
            DotState.PASSED -> setDrawables(Drawables.CIRCLE_ORANGE.drawable)
            else -> {}
        }
    }

    private fun setDrawables(drawable: String) {
        dot1.setDrawable(skin, drawable)
        dot2.setDrawable(skin, drawable)
        dot3.setDrawable(skin, drawable)
    }

    enum class DotState {
        HIDDEN,
        INACCESSIBLE,
        AVAILABLE,
        PASSED
    }

    // TODO animate change state one by one
}

inline fun <S> KWidget<S>.levelDots(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: LevelDots.(S) -> Unit = {}
) = actor(
    LevelDots(
        skin,
    ), init
)
