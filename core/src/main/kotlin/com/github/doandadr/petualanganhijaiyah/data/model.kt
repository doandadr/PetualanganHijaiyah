package com.github.doandadr.petualanganhijaiyah.data

import com.badlogic.gdx.utils.Array
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.collections.gdxArrayOf

enum class StageType(val title: String) {
    MCQ("Tebak Huruf"),
    MCQ_JOIN("Sambungan"),
    MCQ_VOICE("Ucapan"),
    DRAG_AND_DROP("Cetakan"),
    MATCH_LINE("Tarik Garis"),
    DRAWING("Menggambar"),
}

data class StageModel(
    val type: StageType,
    val choices: Int = 3,
    val rounds: Int = 5,
)

data class LevelModel(
    val number: Int,
    val name: String,
    val isTimed: Boolean,
    val timerSeconds: Float,
    val isHealthCounted: Boolean,
    val maxHealth: Int,
    val isScored: Boolean,
    val stages: List<StageModel>,
    val bgIndex: Int,
    val musicIndex: Int,
)

data class LevelSavedData(
    var number: Int = 0,
    var highScore: Float = 0f,
    var recordTime: Float = Float.MAX_VALUE,
    var starCount: Int = 0,
    var hasCompleted: Boolean = false,
)

data class PlayerModel(
    var name: String = " ",
    var character: String = "girl", // boy, girl
    var totalScore: Float = 0f,
    var totalStar: Int = 0,
    var tutorials: MutableSet<Int> = mutableSetOf()
)

enum class Difficulty(
    val timerSeconds: Float,
    val maxHealth: Int,
    val entries: Int,
    val entriesMatch: Int = entries + 2
) {
    EASY(240f, 5, 3),
    MEDIUM(180f, 4, 4),
    HARD(120f, 3, 5)
}

private const val DEFAULT_RECORD_TIME = 300f

val defaultLevelSaveData: Array<LevelSavedData> = gdxArrayOf(
    LevelSavedData(
        number = 1,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 2,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 3,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 4,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 5,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 6,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 7,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 8,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
    LevelSavedData(
        number = 9,
        highScore = 0f,
        recordTime = DEFAULT_RECORD_TIME,
        starCount = 0,
        hasCompleted = false,
    ),
)

val levelsData: Array<LevelModel> = gdxArrayOf(
    LevelModel(
        number = 1,
        name = "Level 1",
        isTimed = true,
        timerSeconds = Difficulty.EASY.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.EASY.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MCQ,
                choices = Difficulty.EASY.entries,
                rounds = 8
            ),
        ),
        bgIndex = TextureAsset.FOREST_1.ordinal,
        musicIndex = MusicAsset.FIELD.ordinal,
    ),
    LevelModel(
        number = 2,
        name = "Level 2",
        isTimed = true,
        timerSeconds = Difficulty.EASY.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.EASY.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MCQ_VOICE,
                choices = Difficulty.EASY.entries,
                rounds = 3
            ),
            StageModel(
                type = StageType.DRAG_AND_DROP,
                choices = Difficulty.EASY.entries,
                rounds = 2
            )
        ),
        bgIndex = TextureAsset.FOREST_1.ordinal,
        musicIndex = MusicAsset.FIELD.ordinal
    ),
    LevelModel(
        number = 3,
        name = "Level 3",
        isTimed = true,
        timerSeconds = Difficulty.EASY.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.EASY.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MATCH_LINE,
                choices = Difficulty.EASY.entriesMatch,
                rounds = 2
            ),
            StageModel(
                type = StageType.MCQ,
                choices = Difficulty.EASY.entries,
                rounds = 4
            ),
        ),
        bgIndex = TextureAsset.FOREST_1.ordinal,
        musicIndex = MusicAsset.FIELD.ordinal
    ),
    LevelModel(
        number = 4,
        name = "Level 4",
        isTimed = true,
        timerSeconds = Difficulty.MEDIUM.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.MEDIUM.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MCQ_JOIN,
                choices = Difficulty.MEDIUM.entries,
                rounds = 2
            ),
            StageModel(
                type = StageType.MCQ_VOICE,
                choices = Difficulty.MEDIUM.entries,
                rounds = 4
            ),
        ),
        bgIndex = TextureAsset.FOREST_2.ordinal,
        musicIndex = MusicAsset.FIELD.ordinal
    ),
    LevelModel(
        number = 5,
        name = "Level 5",
        isTimed = true,
        timerSeconds = Difficulty.MEDIUM.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.MEDIUM.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.DRAWING,
                choices = 1,
                rounds = 4
            ),
        ),
        bgIndex = TextureAsset.FOREST_2.ordinal,
        musicIndex = MusicAsset.FIELD.ordinal
    ),
    LevelModel(
        number = 6,
        name = "Level 6",
        isTimed = true,
        timerSeconds = Difficulty.MEDIUM.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.MEDIUM.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MCQ_VOICE,
                choices = Difficulty.MEDIUM.entries,
                rounds = 4
            ),
            StageModel(
                type = StageType.MATCH_LINE,
                choices = Difficulty.MEDIUM.entriesMatch,
                rounds = 2
            ),
        ),
        bgIndex = TextureAsset.DESERT_1.ordinal,
        musicIndex = MusicAsset.DESERT.ordinal
    ),
    LevelModel(
        number = 7,
        name = "Level 7",
        isTimed = true,
        timerSeconds = Difficulty.HARD.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.HARD.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.DRAG_AND_DROP,
                choices = Difficulty.MEDIUM.entries,
                rounds = 2
            ),
            StageModel(
                type = StageType.MCQ,
                choices = Difficulty.MEDIUM.entries,
                rounds = 3
            ),
            StageModel(
                type = StageType.MCQ_JOIN,
                choices = Difficulty.MEDIUM.entries,
                rounds = 2
            ),
        ),
        bgIndex = TextureAsset.DESERT_1.ordinal,
        musicIndex = MusicAsset.DESERT.ordinal
    ),
    LevelModel(
        number = 8,
        name = "Level 8",
        isTimed = true,
        timerSeconds = Difficulty.HARD.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.HARD.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MATCH_LINE,
                choices = Difficulty.HARD.entriesMatch,
                rounds = 2
            ),
            StageModel(
                type = StageType.DRAWING,
                choices = 1,
                rounds = 2
            ),
        ),
        bgIndex = TextureAsset.DESERT_2.ordinal,
        musicIndex = MusicAsset.DESERT.ordinal
    ),
    LevelModel(
        number = 9,
        name = "Level 9",
        isTimed = true,
        timerSeconds = Difficulty.HARD.timerSeconds,
        isHealthCounted = true,
        maxHealth = Difficulty.HARD.maxHealth,
        isScored = true,
        stages = listOf(
            StageModel(
                type = StageType.MCQ_VOICE,
                choices = Difficulty.HARD.entries,
                rounds = 3
            ),
            StageModel(
                type = StageType.DRAG_AND_DROP,
                choices = Difficulty.HARD.entries,
                rounds = 2
            ),
            StageModel(
                type = StageType.MCQ_JOIN,
                choices = Difficulty.HARD.entries,
                rounds = 2
            ),
        ),
        bgIndex = TextureAsset.DESERT_2.ordinal,
        musicIndex = MusicAsset.DESERT.ordinal
    ),
//    LevelModel(
//        number = 10,
//        name = "Level 10",
//        isTimed = true,
//        timerSeconds = Difficulty.EASY.timerSeconds,
//        isHealthCounted = true,
//        maxHealth = Difficulty.EASY.maxHealth,
//        isScored = true,
//        stages = listOf(
//            StageModel(
//                type = StageType.MCQ,
//                entries = Difficulty.HARD.entries,
//                rounds = 4
//            ),
//            StageModel(
//                type = StageType.MCQ_VOICE,
//                entries = Difficulty.HARD.entries,
//                rounds = 3
//            ),
//            StageModel(
//                type = StageType.DRAG_AND_DROP,
//                entries = Difficulty.HARD.entries,
//                rounds = 2
//            ),
//            StageModel(
//                type = StageType.MATCH_LINE,
//                entries = Difficulty.HARD.entriesMatch,
//                rounds = 2
//            ),
//            StageModel(
//                type = StageType.MCQ_JOIN,
//                entries = Difficulty.HARD.entries,
//                rounds = 2
//            ),
//            StageModel(
//                type = StageType.DRAWING,
//                entries = 1,
//                rounds = 1
//            ),
//        ),
//        bgIndex = TextureAsset.DESERT_2.ordinal,
//        musicIndex = MusicAsset.DESERT.ordinal
//    )
)



