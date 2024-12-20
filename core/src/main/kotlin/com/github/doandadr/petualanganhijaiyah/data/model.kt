package com.github.doandadr.petualanganhijaiyah.data

import com.badlogic.gdx.utils.Array
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.collections.gdxArrayOf

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
        musicIndex = MusicAsset.DESERT_ALT.ordinal
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
        musicIndex = MusicAsset.DESERT_ALT.ordinal
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
        musicIndex = MusicAsset.DESERT_ALT.ordinal
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
        musicIndex = MusicAsset.DESERT_ALT.ordinal
    ),
)



