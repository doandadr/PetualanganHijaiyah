package com.github.doandadr.petualanganhijaiyah.data

enum class StageType {
    MULTIPLE_CHOICE_LETTER,
    MULTIPLE_CHOICE_JOIN,
    MULTIPLE_CHOICE_VOICE,
    DRAG_AND_DROP,
    MATCH_LINE,
    DRAWING,
}

data class StageModel(
    val type: StageType,
    val entries: Int = 3, // 1 - 6
    val rounds: Int = 5, // 1 - 5?
)

// enter map screen
// map screen reads from saved data, starCount and hasCompleted
// level start
// level retrieves data from model: name, number, etc., and setup first stage in list
// start timer/stopwatch,
// go to stage logic
    // player plays the game until either it is complete or fail or canceled
        // player plays n rounds of the stage, then move on to next in stageList if available
        // if skip, repeat round
        // if timer is available,
        // if health is available, reduce health if incorrect, and if heart drops to 0 go to failure state EVENT,
        // health does not affect score, just make sure that timer is challenging
    // if complete, show finish screen, update save data, EVENT
    // if fail, show finish screen, EVENT
    // if , clear screen as usual
    // either way, stop timer

data class LevelModel(
    // TODO set values
    val name: String,
    val number: Int,

    val isTimed: Boolean = true,
    val timerSeconds: Float = 120f,
    val isHealthCounted: Boolean = true,
    val maxHealth: Int = 5,
    val isScored: Boolean = true,
    val stages: MutableList<StageModel>,
)

data class LevelSavedData(
    val highScore: Float,
    val quickestTimeSeconds: Float,
    val starCount: Int,
    val hasCompleted: Boolean,
)

fun getLevelModelDemo(): LevelModel = LevelModel(
    name = "LEVEL 1",
    number = 1,
    isTimed = true,
    timerSeconds = 120f,
    isHealthCounted = true,
    maxHealth = 5,
    isScored = true,
    stages = getStagesDemo(),
)
fun getStagesDemo(): MutableList<StageModel> {
    val stageList : MutableList<StageModel> = mutableListOf()
    val stage1 = StageModel(
        type = StageType.MULTIPLE_CHOICE_LETTER,
        entries = 3,
        rounds = 5
    )
    val stage2 = StageModel(
        StageType.MULTIPLE_CHOICE_VOICE,
        entries = 3,
        rounds = 5
    )
    stageList.add(stage1)
    stageList.add(stage2)
    return stageList
}
fun getLevelDataDemo() : LevelSavedData = LevelSavedData(
    highScore = 666f,
    quickestTimeSeconds = 60f,
    starCount = 2,
    hasCompleted = true
)

