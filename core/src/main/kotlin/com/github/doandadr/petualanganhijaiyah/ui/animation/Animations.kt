package com.github.doandadr.petualanganhijaiyah.ui.animation

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction

class Animations {
    companion object {
        // STAR ANIMATION
        fun starAnimation() : SequenceAction {
            val duration = 0.5f

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
        fun pulseAnimation() : SequenceAction {
            val duration = 0.3f

            val scaleUp = Actions.scaleBy(0.1f, 0.1f, duration, Interpolation.fade)
            val scaleDown = Actions.scaleBy(-0.1f, -0.1f, duration, Interpolation.fade)

            val action = Actions.sequence(scaleUp, scaleDown)
            return action
        }

        // FADE IN & OUT
        fun fadeInOutAnimation(): SequenceAction {
            val fadeDuration = 0.2f
            val stillDuration = 0.5f

            val fadeIn = Actions.fadeIn(fadeDuration)
            val delay = DelayAction(stillDuration)
            val fadeOut = Actions.fadeOut(fadeDuration)

            val action = Actions.sequence(fadeIn, delay, fadeOut)
            return action
        }

        // POP IN
        fun popInAnimation(): SequenceAction {
            val duration = 0.3f

            val initScale = Actions.scaleTo(0.1f, 0.1f)
            val show = Actions.show()
            val popIn = Actions.scaleTo(1f, 1f, duration, Interpolation.fade)

            val action = Actions.sequence(initScale, show, popIn)
            return action
        }
    }
}
