package com.github.doandadr.petualanganhijaiyah.event

import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.screen.HomeScreen

interface GameEventListener {
    fun answerCorrect(isContinue: Boolean) {}
    fun answerIncorrect(isContinue: Boolean){}
    fun showTutorial(tutorialKey: String) {}
    fun levelComplete(score: Float, stars: Int, time: Float) {}
    fun levelFailed() {}
    fun setHomePopupState(state: HomeScreen.PopupState) {}
    fun timerRunsOut() {}
    fun healthDepleted(maxHealth: Int) {}
    fun playerChanged(player: PlayerModel) {}
}
