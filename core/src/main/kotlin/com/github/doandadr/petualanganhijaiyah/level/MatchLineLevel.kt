package com.github.doandadr.petualanganhijaiyah.level

private const val MATCH_LINE_LETTER_COUNT = 6
class MatchLineLevel(
    private val letterCount: Int,
    private val isTimed: Boolean,
) {


}

// DATA involved
// random number collection of letter ids
// function to determine whether answer is correct (matched or not)
// two lists of hijaiyah, their respective "circles"
// if circle matches, correct, add score, coins etc
// have n lives, if answer wrong decrease from that life count
// if life go to 0 then play level failed + try again
// if timed, when time is 0 level failed+ try again
// level number, save in parent?
// tooltip sequence?
