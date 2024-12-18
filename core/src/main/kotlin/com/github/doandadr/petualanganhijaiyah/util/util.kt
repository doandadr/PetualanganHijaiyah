package com.github.doandadr.petualanganhijaiyah.util

import com.github.doandadr.petualanganhijaiyah.SCREEN_H
import com.github.doandadr.petualanganhijaiyah.SCREEN_W

// Center actors on screen
fun centerX(x:Float) = SCREEN_W/2f-x/2f
fun centerY(y:Float) = SCREEN_H/2f-y/2f

//fun Actor.applyButtonProperties(audioService: AudioService, sound: SoundAsset = SoundAsset.CLICK_BUTTON, animation: Action = Animations.pulseAnimation()) {
//    setOrigin(Align.center)
//    onTouchDown {
//        this.clearActions()
//        this += Animations.pulseAnimation()
//        audioService.play(SoundAsset.CLICK_BUTTON)
//    }
//}
