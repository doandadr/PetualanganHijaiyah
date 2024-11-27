package com.github.doandadr.petualanganhijaiyah.event

import com.badlogic.gdx.utils.Array
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah

interface GameEventListener {
    // TODO define logic, parameters and function body
    fun answerCorrect() {}
    // either level/stage data or number of lives
    fun answerIncorrect() {}
    fun levelFailed() {}
    fun levelSuccess() {} // Level data, should be in class
    fun showTutorial(tutorialKey: String) {}
}

class LevelState {
    var levelNum = 0
    var isCompleted = false
    var star = 0
    var score = 0


}

class StageState{
    // 3 choices, 1 correct
    // MCQ, MatchLine,
    var choices : Array<Hijaiyah>? = null
    var correct : Array<Hijaiyah>? = null
    //
}

enum class StagetType {
    PRACTICE_LETTER_VOICE,
    PRACTICE_DRAWING,
    MULTIPLE_CHOICE_LETTER,
    MULTIPLE_CHOICE_JOIN,
    MULTIPLE_CHOICE_VOICE,
    DRAG_AND_DROP,
    MATCH_LINE,
    DRAWING,
}
