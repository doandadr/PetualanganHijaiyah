package com.github.doandadr.petualanganhijaiyah.ui.animation

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import ktx.actors.plus

object Animations {
    fun starAnimation(duration: Float = 0.5f): SequenceAction {
        val showStart = Actions.show()
        val alphaStart = Actions.alpha(0f)
        val moveByStart = Actions.moveBy(-20f, -20f)
        val scaleToStart = Actions.scaleBy(4f, 4f)
        val rotateByStart = Actions.rotateBy(-360f)
        val start =
            Actions.parallel(showStart, alphaStart, moveByStart, scaleToStart, rotateByStart)

        val alpha = Actions.fadeIn(duration)
        val moveBy = Actions.moveBy(20f, 20f, duration, Interpolation.fade)
        val scaleTo = Actions.scaleBy(-4f, -4f, duration, Interpolation.fade)
        val rotateBy = Actions.rotateBy(360f, duration, Interpolation.fade)
        val animation = Actions.parallel(alpha, moveBy, scaleTo, rotateBy)

        return start + animation
    }

    fun pulseAnimation(
        scale: Float = 0.2f,
        initScale: Float = 1f,
        duration: Float = 0.3f
    ): SequenceAction {
        val scaleTo = Actions.scaleTo(initScale, initScale)
        val scaleUp = Actions.scaleBy(scale, scale, duration, Interpolation.fade)
        val scaleDown = Actions.scaleBy(-scale, -scale, duration, Interpolation.fade)

        return scaleTo + scaleUp + scaleDown
    }

    fun fadeInOutAnimation(
        fadeDuration: Float = 0.2f,
        stillDuration: Float = 0.5f
    ): SequenceAction {
        val fadeIn = Actions.fadeIn(fadeDuration)
        val delay = DelayAction(stillDuration)
        val fadeOut = Actions.fadeOut(fadeDuration)

        return fadeIn + delay + fadeOut
    }

    fun popInAnimation(duration: Float = 0.3f): SequenceAction {
        val initScale = Actions.scaleTo(0.1f, 0.1f)
        val show = Actions.show()
        val popIn = Actions.scaleTo(1f, 1f, duration, Interpolation.fade)

        return initScale + show + popIn
    }
}
