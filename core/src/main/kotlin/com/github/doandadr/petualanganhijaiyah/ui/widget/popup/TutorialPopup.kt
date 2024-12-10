package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import ktx.log.logger
import ktx.scene2d.*
import java.util.*

enum class TutorialType(
    val lines: Array<String>,
) {
    HOME_NAME(arrayOf("Perkenalkan diri yuk!")),
    HOME_PRACTICE(arrayOf("Belajar dulu yuk!")),
    HOME_START(arrayOf("Ayo mulai!")),

    MAP_LEVEL1(arrayOf("Click [ORANGE]Level 1 []untuk memulai permainan!")),

}

class Tutorial() {
    companion object {
        val tutorials: EnumSet<TutorialType> = EnumSet.noneOf(TutorialType::class.java)
    }
}

class TutorialPopup(
    private val preferences: Preferences,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
): Table(skin), KTable {

    private var tutorialLabel: Label

    init {
        this@TutorialPopup.tutorialLabel = label("This is a tutorial message", Labels.TUTORIAL.style) {
        }
        loadTutorial()
    }

    fun loadTutorial() {

    }


    fun setLabelPosition(x: Float, y:Float) {

    }

    fun setLabelPosition(vec: Vector2) {

    }

    enum class ORIENTATION(style: String) {
        LEFT_UP(Labels.TUTORIAL_LEFT_UP.style),
        LEFT_DOWN(Labels.TUTORIAL_LEFT_DOWN.style),
        RIGHT_UP(Labels.TUTORIAL_RIGHT_UP.style),
        RIGHT_DOWN(Labels.TUTORIAL_RIGHT_DOWN.style)
    }

    companion object {
        private val log = logger<TutorialPopup>()
    }
}


inline fun <S> KWidget<S>.tutorialPopup(
    preferences: Preferences,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    init: TutorialPopup.(S) -> Unit = {}
) = actor(
    TutorialPopup(
        preferences,
        audioService,
        gameEventManager,
    ), init
)

