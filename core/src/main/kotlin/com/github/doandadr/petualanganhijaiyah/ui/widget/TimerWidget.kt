package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_BETWEEN_BUTTONS
import ktx.scene2d.KTable
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor

class TimerWidget(
    private val maxSeconds: Float,
    private val eventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    // ON level finish event, stop timer, OK
    // IF timer drops to 0, send failure state to parent TODO

    private var deltaTimerCount: Float = 0f
    var remainingSeconds: Float = maxSeconds
    var elapsedSeconds: Float = 0f

    private val counter: TimerCounter
    private val bar: TimerBar
    private var state: State = State.START

    init {
        this@TimerWidget.bar = timerBar(maxSeconds) {
            it.align(Align.topLeft)
        }

        row()
        this@TimerWidget.counter = timerCounter(maxSeconds) {
            it.spaceTop(SPACE_BETWEEN_BUTTONS).align(Align.topLeft)
        }
    }

    fun update(deltaTime: Float) {
        // TODO update only if resume
        if (state == State.START) {
            deltaTimerCount += deltaTime
            if (deltaTimerCount >= 1) {
                setState(State.RESUME)
                deltaTimerCount = 0f
            }
            counter.resetTimer()
            bar.resetTimer()
        }
        if (state == State.RESUME) {
            deltaTimerCount += deltaTime
            if (deltaTimerCount >= 1) {
                remainingSeconds -= 1
                elapsedSeconds += 1
                counter.updateTime(remainingSeconds)
                bar.updateTime(remainingSeconds)
                deltaTimerCount = 0f
            }
        }
        if (state == State.STOP) {
            remainingSeconds = maxSeconds
            counter.updateTime(0f)
            bar.updateTime(0f)
        }
        if (state == State.STOPWATCH) {
            deltaTimerCount += deltaTime
            if (deltaTimerCount >= 1) {
                elapsedSeconds += 1
                counter.updateTime(elapsedSeconds)
            }
        }
        if (remainingSeconds <= 0f) {
            // TODO handle level end, failure
            setState(State.STOP)
            eventManager.dispatchLevelFailEvent(elapsedSeconds)
        }
    }

    fun setState(timerState: State) {
        state = timerState
        bar.isVisible = (state != State.STOPWATCH)
        isVisible = (state != State.DISABLED)
    }

    enum class State {
        STOP,
        RESUME,
        PAUSE,
        DISABLED,
        STOPWATCH,
        START,
    }

}

inline fun <S> KWidget<S>.timerWidget(
    maxSeconds: Float,
    eventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: TimerWidget.(S) -> Unit = {}
) = actor(
    TimerWidget(
        maxSeconds,
        eventManager,
        skin
    ), init
)
