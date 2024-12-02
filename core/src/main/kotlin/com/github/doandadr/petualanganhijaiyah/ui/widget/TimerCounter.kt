package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import ktx.scene2d.*

class TimerCounter(
    private val maxSeconds: Float,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    private val colon: Image
    val minute: Label
    val second: Label

    init {
        minute = label("", Labels.TIMER_COUNTER.style) {
            setAlignment(Align.center)
        }
        colon = image(skin.getDrawable(Drawables.TIMER_COUNTER_DOTS.drawable))
        second = label("", Labels.TIMER_COUNTER.style) {
            setAlignment(Align.center)
        }

        resetTimer()
    }

    fun updateTime(time: Float) {
        minute.setText(formatMM(time))
        second.setText(formatSS(time))
    }

    fun resetTimer() {
        updateTime(maxSeconds)
    }

    private fun formatMM(sec: Float): String = (sec / 60).toInt().toString().padStart(2, '0')

    private fun formatSS(sec: Float): String = (sec % 60).toInt().toString().padStart(2, '0')
}

inline fun <S> KWidget<S>.timerCounter(
    setSeconds: Float,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: TimerCounter.(S) -> Unit = {}
) = actor(
    TimerCounter(
        setSeconds
    ), init
)
