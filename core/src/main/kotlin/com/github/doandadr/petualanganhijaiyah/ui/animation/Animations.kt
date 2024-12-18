package com.github.doandadr.petualanganhijaiyah.ui.animation

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction

object Animations {
    // STAR ANIMATION
    fun starAnimation(duration: Float = 0.5f): SequenceAction {
        val showStart = Actions.show()
        val alphaStart = Actions.alpha(0f)
        val moveByStart = Actions.moveBy(-20f, -20f)
        val scaleToStart = Actions.scaleBy(4f, 4f)
        val rotateByStart = Actions.rotateBy(-360f)
        val start = Actions.parallel(showStart, alphaStart, moveByStart, scaleToStart, rotateByStart)

        val alpha = Actions.fadeIn(duration)
        val moveBy = Actions.moveBy(20f, 20f, duration, Interpolation.fade)
        val scaleTo = Actions.scaleBy(-4f, -4f, duration, Interpolation.fade)
        val rotateBy = Actions.rotateBy(360f, duration, Interpolation.fade)
        val animation = Actions.parallel(alpha, moveBy, scaleTo, rotateBy)

        val action = Actions.sequence(start, animation)
        return action
    }

    // PULSE ANIMATION
    fun pulseAnimation(scaleValue: Float = 0.2f, duration: Float = 0.3f): SequenceAction {
        val scaleUp = Actions.scaleBy(scaleValue, scaleValue, duration, Interpolation.fade)
        val scaleDown = Actions.scaleBy(-scaleValue, -scaleValue, duration, Interpolation.fade)

        val action = Actions.sequence(scaleUp, scaleDown)
        return action
    }

    // FADE IN & OUT
    fun fadeInOutAnimation(fadeDuration: Float = 0.2f, stillDuration: Float = 0.5f): SequenceAction {
        val fadeIn = Actions.fadeIn(fadeDuration)
        val delay = DelayAction(stillDuration)
        val fadeOut = Actions.fadeOut(fadeDuration)

        val action = Actions.sequence(fadeIn, delay, fadeOut)
        return action
    }

    // POP IN
    fun popInAnimation(duration: Float = 0.3f): SequenceAction {
        val initScale = Actions.scaleTo(0.1f, 0.1f)
        val show = Actions.show()
        val popIn = Actions.scaleTo(1f, 1f, duration, Interpolation.fade)

        val action = Actions.sequence(initScale, show, popIn)
        return action
    }
}
