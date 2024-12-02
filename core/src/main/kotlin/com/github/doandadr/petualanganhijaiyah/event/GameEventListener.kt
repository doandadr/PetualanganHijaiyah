package com.github.doandadr.petualanganhijaiyah.event

interface GameEventListener {
    // TODO define logic, parameters and function body
    fun answerCorrect() {}
    // either level/stage data or number of lives
    fun answerIncorrect() {}
    fun levelFailed(elapsedSeconds: Float) {}
    fun levelSuccess() {} // Level data, should be in class
    fun showTutorial(tutorialKey: String) {}
}
//class LevelConfig {
//    var levelNum = 0
//    var isCompleted = false
//    var star = 0
//    var score = 0
//
//
//}
//
//class StageState{
//    // 3 choices, 1 correct
//    // MCQ, MatchLine,
//    var choices : Array<Hijaiyah>? = null
//    var correct : Array<Hijaiyah>? = null
//    //
//}

