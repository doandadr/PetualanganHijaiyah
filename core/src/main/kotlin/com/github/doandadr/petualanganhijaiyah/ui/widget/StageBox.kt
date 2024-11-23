package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.doandadr.petualanganhijaiyah.SCREEN_H
import com.github.doandadr.petualanganhijaiyah.SCREEN_W
import com.github.doandadr.petualanganhijaiyah.util.centerX
import com.github.doandadr.petualanganhijaiyah.util.centerY
import ktx.scene2d.KTable
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor
import ktx.style.skin

enum class StageType {
    MULTIPLE_CHOICE_LETTER,
    MULTIPLE_CHOICE_JOIN,
    MULTIPLE_CHOICE_VOICE,
    DRAG_AND_DROP,
    MATCH_LINE,
    DRAWING,
}

open class StageBox(
    skin: Skin,
    val bismillahImage: Image = Image(),
    // game board
) : Table(skin), KTable {
    private lateinit var stageType: StageType

    // TODO set init position(center, 80% screen size)
    init {
        val sizePercentX = 0.8f
        val sizePercentY = 0.8f
        setSize(SCREEN_W * sizePercentX, SCREEN_H * sizePercentY)
        setPosition(centerX(width), centerY(height))
    }

    fun setStageType(type: StageType) {
        stageType = type
    }
    // Life system where the user have three chances of making a mistake, and it is optional
    // Timer system where the level starts at maximum score, then the score or timer depletes over time and/or when user makes a mistake and loses health, when the score and timer reaches 0 or when user lost health, then level fails
    // A level can have many types of stages, but mostly only one type
    // A level consists of N amount of stages
    // Stages of the level's type is taken randomly from a list of stage data (question and answer), OR from random
    // A level consists of N amount of stars/score
    // Both stars and score are dictated by a timer/how many wrongs per stage/usage of passes or coins
    // Maximum score of 100, stars is (20 - 1 star, 50 - 2 star, 80 - 3 star)
    // Passing once
    // A stage has different types (match line etc)

    // TODO setScore
    // TODO setStar
    // TODO correct incorrect
    // TODO
    // These are duplicate functions that manages the states of the stage
}

inline fun <S> KWidget<S>.stageBox(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: StageBox.(S) -> Unit = {}
) = actor(
    StageBox(
        skin,
    ), init
)

class MultipleChoiceLetterStage(skin: Skin) : StageBox(skin) {

}

inline fun <S>KWidget<S>.multipleChoiceLetterStage(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: MultipleChoiceLetterStage.(S) -> Unit
) = actor(
    MultipleChoiceLetterStage(
        skin
    ),init
)
