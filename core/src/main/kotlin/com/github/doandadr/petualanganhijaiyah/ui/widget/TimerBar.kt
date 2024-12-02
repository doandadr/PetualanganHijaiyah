package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import ktx.scene2d.*

class TimerBar(
    // IF value drop below n, change star view based on that
    private val maxSeconds: Float,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    private var currentValue: Float = MAX_VALUE
    private var greenBar: Image
    private val barMeter: Table
    private val starOnFirst: Image
    private val starOffFirst: Image
    private val starOnSecond: Image
    private val starOffSecond: Image
    private val starOnThird: Image
    private val starOffThird: Image

    init {
        stack {
            image(Drawables.TIMER_BACKGROUND_EMPTY.drawable) {
                setScaling(Scaling.stretchX)
            }
            this@TimerBar.barMeter = table {
                align(Align.left)
                padLeft(11f)
                padTop(1f)

                this@TimerBar.greenBar = image(Drawables.TIMER_BACKGROUND_GREEN.drawable) {
                    setScaling(Scaling.stretchX)
                    width = WIDTH_SPAN
                }
            }
            image(Drawables.TIMER_FRAME_ORANGE.drawable) {
                setScaling(Scaling.stretchX)
                align = Align.left
            }
            it.padBottom(-65.0f).colspan(10)
        }

        row()
        add().colspan(3)
        stack {
            this@TimerBar.starOffFirst = image(Drawables.ICON_STARGREY_SMALL.drawable)
            this@TimerBar.starOnFirst = image(Drawables.ICON_STAR_SMALL.drawable)
            it.prefSize(60f).padLeft(-30f).align(Align.left).colspan(3)
        }
        stack {
            this@TimerBar.starOffSecond = image(Drawables.ICON_STARGREY_SMALL.drawable)
            this@TimerBar.starOnSecond = image(Drawables.ICON_STAR_SMALL.drawable)
            it.prefSize(60f).padLeft(-30f).align(Align.left).colspan(2)
        }
        stack {
            this@TimerBar.starOffThird = image(Drawables.ICON_STARGREY_SMALL.drawable)
            this@TimerBar.starOnThird = image(Drawables.ICON_STAR_SMALL.drawable)
            it.prefSize(60f).padLeft(-30f).align(Align.left).colspan(2)
        }

        resetTimer()
    }

    private fun valueToWidth(value: Float): Float {
        return MathUtils.clamp(value / MAX_VALUE * WIDTH_SPAN , 0f, WIDTH_SPAN)
    }

    fun updateTime(time: Float) {
        currentValue = time / maxSeconds * MAX_VALUE
        greenBar.width = valueToWidth(currentValue)
        setStars(currentValue)
    }

    private fun setStars(value: Float) {
        if (value > THIRD_THRESHOLD) {
            starOnFirst.isVisible = true
            starOnSecond.isVisible = true
            starOnThird.isVisible = true
        }
        if (value <= THIRD_THRESHOLD)
            starOnThird.isVisible = false
        if (value <= SECOND_THRESHOLD)
            starOnSecond.isVisible = false
        if (value <= FIRST_THRESHOLD)
            starOnFirst.isVisible = false
    }

    fun resetTimer() {
        updateTime(maxSeconds)
    }

    companion object {
        private const val MAX_VALUE = 100f
        private const val WIDTH_SPAN = 330f
        private const val FIRST_THRESHOLD = 30f
        private const val SECOND_THRESHOLD = 60f
        private const val THIRD_THRESHOLD = 80f
    }
}

inline fun <S> KWidget<S>.timerBar(
    timerSeconds: Float,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: TimerBar.(S) -> Unit = {}
) = actor(
    TimerBar(
        timerSeconds,
        skin
    ), init
)

