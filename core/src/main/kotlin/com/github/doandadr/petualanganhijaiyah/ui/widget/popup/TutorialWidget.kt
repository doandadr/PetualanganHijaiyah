package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.Tutorial.Orientation
import com.kotcrab.vis.ui.layout.FloatingGroup
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.plusAssign
import ktx.log.logger
import ktx.math.plus
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*
import java.util.*

enum class TutorialType(
    val pages: List<String>,
) {
    HOME_NAME(listOf("Perkenalkan diri yuk!")),
    HOME_PRACTICE(listOf("Belajar huruf hijaiyah disini!")),
    HOME_START(listOf("Mulai petualanganmu!")),

    NAME_CHANGE(listOf("Ketikkan namamu disini!")),

    MAP_LEVEL1(listOf("Click [ORANGE]Level 1 []untuk memulai permainan!")),
    MAP_LEVEL9(listOf("Semangat! sebentar lagi kamu dapat menyelesaikan misi")),

    PRACTICE_NEXT(listOf("Lanjut")),

    MATCH_START(listOf("Tarik garis dari [ORANGE]huruf hijaiyah,[]")),
    MATCH_END(listOf("Lalu arahkan sesuai [ORANGE]eja-an []huruf hijaiyah tersebut!")),

    DRAW_START(
        listOf(
            "Gambarlah [ORANGE]huruf hijaiyah []di dalam kotak kosong,",
            "sesuai dengan yang tertulis di kotak kecil."
        )
    ),
    VOICE_START(listOf("Klik disini untuk mendengarkan bunyi huruf hijaiyah nya")),
    VOICE_OPTION(
        listOf(
            "Lalu, pilihlah salah satu [ORANGE]huruf hijaiyah",
            "[]yang sesuai dengan bunyi yang kamu dengar!"
        )
    )
}

class Tutorial(
    val type: TutorialType,
    actor: Actor,
    stage: Stage
) {
    private var targetPos: Vector2
    private var targetSize: Vector2
    private var stageSize: Vector2

    lateinit var position: Vector2
    lateinit var offset: Vector2
    lateinit var orientation: Orientation

    init {
        targetPos = actor.localToStageCoordinates(Vector2())
        targetSize = Vector2(actor.width, actor.height)
        stageSize = Vector2(stage.width, stage.height)
        log.debug { "targetPos:$targetPos ,targetSize:$targetSize ,stageSize:$stageSize" }

        calculate()
    }

    private fun calculate() {
        orientation = calculateOrientation(targetPos, stageSize)
        offset = calculateOffset(orientation, targetSize)
        position = calculatePosition(offset, targetPos)
        log.debug { "Calculated orientation:$orientation, offset:$offset, position:$position" }
    }

    private fun calculateOrientation(actorPos: Vector2, stageSize: Vector2): Orientation {
        return when {
            actorPos.x < stageSize.x / 2 && actorPos.y < stageSize.y / 2 -> Orientation.LEFT_DOWN
            actorPos.x < stageSize.x / 2 && actorPos.y >= stageSize.y / 2 -> Orientation.LEFT_UP
            actorPos.x >= stageSize.x / 2 && actorPos.y < stageSize.y / 2 -> Orientation.RIGHT_DOWN
            else -> Orientation.RIGHT_UP
        }
    }

    private fun calculateOffset(orientation: Orientation, actorSize: Vector2): Vector2 {
        return when (orientation) {
            Orientation.LEFT_DOWN -> Vector2(0f, actorSize.y)
            Orientation.LEFT_UP -> Vector2(0f, -actorSize.y)
            Orientation.RIGHT_DOWN -> Vector2(actorSize.x, actorSize.y)
            Orientation.RIGHT_UP -> Vector2(actorSize.x, -actorSize.y)
        }
    }

    private fun calculatePosition(offset: Vector2, targetPos: Vector2): Vector2 {
        return targetPos + offset
    }

    enum class Orientation(
        val style: String,
    ) {
        LEFT_UP(Labels.TUTORIAL_LEFT_UP.style),
        LEFT_DOWN(Labels.TUTORIAL_LEFT_DOWN.style),
        RIGHT_UP(Labels.TUTORIAL_RIGHT_UP.style),
        RIGHT_DOWN(Labels.TUTORIAL_RIGHT_DOWN.style)
    }

    companion object {
        private val log = logger<Tutorial>()
    }
}

class TutorialWidget(
    private val preferences: Preferences,
    private val gameEventManager: GameEventManager,
    private val skin: Skin = Scene2DSkin.defaultSkin,
) : FloatingGroup(), KGroup {
    private val player = preferences[PrefKey.PLAYER.key, PlayerModel()]
    private var tutorialQueue: Queue<Tutorial> = LinkedList()
    private lateinit var tutorial: Tutorial
    private var currentPageIndex = 0
    private var isInitialized = false

    private var tutorialLabel: Label = label("") {
        setAlignment(Align.center)
        setFontScale(SCALE_FONT_SMALL)
    }

    init {
        setFillParent(true)
        isVisible = false
        touchable = Touchable.enabled
        onClick { nextLine() }
    }

    private fun loadTutorial() {
        val tutorialText = tutorial.type.pages[currentPageIndex]
        tutorialLabel.setText(tutorialText)
        log.debug { "Showing tutorial $tutorialText" }
        tutorialLabel.pack()

        tutorialLabel.style = skin.get(tutorial.orientation.style, Label.LabelStyle::class.java)

        tutorialLabel.pack()
        var coords = tutorial.position
        if (tutorial.orientation in listOf(Orientation.RIGHT_UP, Orientation.RIGHT_DOWN))
            coords += Vector2(-tutorialLabel.width, 0f)
        tutorialLabel.pack()

        tutorialLabel.setPosition(coords.x, coords.y)
        log.debug { "Label position(${coords.x}, ${coords.y}), label size(${tutorialLabel.width}, ${tutorialLabel.height})" }
        tutorialLabel.pack()
    }

    private fun nextLine() {
        if (isInitialized) {
            isInitialized = false
            tutorialQueue.poll()
        }
        if (tutorial.type.pages.getOrNull(currentPageIndex + 1) != null && isInitialized) {
            currentPageIndex++
            loadTutorial()
        } else {
            setTutorialFromQueue()
        }
    }

    private fun hideTutorial(fadeTime: Float = 0.3f) {
        this.clearActions()
        this += fadeOut(fadeTime) + hide()
    }

    private fun showTutorial(delayTime: Float = 0.2f) {
        if (tutorialQueue.peek() != null) {
            this.clearActions()
            this += delay(delayTime) + show() + fadeIn(0.3f)
            isInitialized = true
            loadTutorial()
        }
    }

    fun addTutorial(actor: Actor, type: TutorialType) {
        registerTutorial(player, type, actor)
        showTutorial()
    }

    private fun registerTutorial(player: PlayerModel, type: TutorialType, actor: Actor) {
//        if (player.tutorials.add(type.ordinal)) {
            tutorialQueue.add(Tutorial(type, actor, stage))
            tutorial = tutorialQueue.peek()
            updatePlayer()
//        }
    }

    private fun updatePlayer() {
        preferences.flush {
            this[PrefKey.PLAYER.key] = player
        }
    }

    private fun setTutorialFromQueue() {
        val entry = tutorialQueue.poll()
        if (entry != null) {
            tutorial = entry
            currentPageIndex = 0
            loadTutorial()
        } else {
            hideTutorial()
        }
    }

    companion object {
        private val log = logger<TutorialWidget>()
    }
}

inline fun <S> KWidget<S>.tutorialWidget(
    preferences: Preferences,
    gameEventManager: GameEventManager,
    init: TutorialWidget.(S) -> Unit = {}
) = actor(
    TutorialWidget(
        preferences,
        gameEventManager,
    ), init
)

