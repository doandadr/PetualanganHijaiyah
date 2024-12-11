package com.github.doandadr.petualanganhijaiyah.ui.widget.popup

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.Tutorial.Orientation
import com.kotcrab.vis.ui.layout.FloatingGroup
import ktx.log.logger
import ktx.math.minus
import ktx.math.minusAssign
import ktx.math.plus
import ktx.scene2d.*

enum class TutorialType(
    val lines: List<String>,
) {
    HOME_NAME(listOf("Perkenalkan diri yuk!")),
    HOME_PRACTICE(listOf("Belajar dulu yuk!")),
    HOME_START(listOf("Ayo mulai!")),

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

// DATA
// WHAT
    // Tutorial type(type, content, how to identify in preferences),
// WHERE
    // Actor, actor's position AFTER rendering, so init on popup, set dynamically, (BEFORE or AFTER render),
        // Register actor in separate method, After setup UI
    // Position of the tutorial label based on the target actor position
    // Orientation of the tutorial label based on the target actor position

// NOTES
    // when setText(), width only changes AFTER pack()
    // we need to calculate location EVERY setText()

// FLOW
    // First, load data (preferences -> player -> already done tuts), set of ordinals/index of tut type
    // Set what tutorial types are needed in the screen/stage/class
    // For each tutorial type, check if needed tutorials are already done
        // IF true -> don't continue
        // ELSE -> setup info(see DATA section), for each actors: -> do in postRunnable
            // calculate what the position of the tut label
            // calculate what the orientation of the tut label
    // Show Certain Popups according to conditionals -> (Start of screen, start of stage)


class Tutorial(
    val type: TutorialType,
    val targetPos: Vector2,
    private val targetSize: Vector2,
    private val stageSize: Vector2,
) {
    lateinit var position: Vector2
    lateinit var offset: Vector2
    lateinit var orientation: Orientation
    lateinit var styleName: String



    enum class Orientation(
        val style: String,
    ) {
        LEFT_UP(Labels.TUTORIAL_LEFT_UP.style),
        LEFT_DOWN(Labels.TUTORIAL_LEFT_DOWN.style),
        RIGHT_UP(Labels.TUTORIAL_RIGHT_UP.style),
        RIGHT_DOWN(Labels.TUTORIAL_RIGHT_DOWN.style)
    }

    private fun calculateOrientation(actorPos: Vector2, stageSize: Vector2) : Orientation {
        return when {
            actorPos.x < stageSize.x / 2 && actorPos.y < stageSize.y / 2 -> Orientation.LEFT_DOWN
            actorPos.x < stageSize.x / 2 && actorPos.y >= stageSize.y / 2 -> Orientation.LEFT_UP
            actorPos.x >= stageSize.x / 2 && actorPos.y < stageSize.y / 2 -> Orientation.RIGHT_DOWN
            else -> Orientation.RIGHT_UP
        }
    }

    private fun calculateOffsetByOrientation(orientation: Orientation, actorSize: Vector2): Vector2 {
        return when (orientation) {
            Orientation.LEFT_DOWN ->    Vector2(0f, actorSize.y)
            Orientation.LEFT_UP ->       Vector2(0f, -actorSize.y)
            Orientation.RIGHT_DOWN ->    Vector2(actorSize.x, actorSize.y)
            Orientation.RIGHT_UP ->     Vector2(actorSize.x, -actorSize.y)
        } // TODO if Orientation is right up or right down, offset - (tutorialLabel.width, 0)
    }

    private fun calculateTutorialPosition(targetPos: Vector2, offset: Vector2): Vector2 {
        return targetPos + offset
    }


    init {
//        //// TODO
//        val tutorialText = TutorialType.HOME_PRACTICE.lines.first()
//        tutorialLabel.setText(tutorialText)
//        tutorialLabel.pack()
//        ///// TODO


        ///// STYLE NAME
        orientation = calculateOrientation(targetPos, stageSize)
        ////////


        ///////// OFFSET
        offset = calculateOffsetByOrientation(orientation, targetSize)
        //////////////

//        ///// TODO
//        val skin = Scene2DSkin.defaultSkin
//        tutorialLabel.style = skin.get(styleName, Label.LabelStyle::class.java)
//        ///// TODO

        //////// POSITION
        if (orientation in listOf( Orientation.RIGHT_UP, Orientation.RIGHT_DOWN))
            offset  = offset - Vector2(tutorialLabel.width)
        ////////

        position = calculateTutorialPosition(targetPos, offset)

//        ///// TODO
//        tutorialLabel.setPosition(stageLoc.x, stageLoc.y)
//        log.debug { "tutorialLabel.setPosition(${stageLoc.x}, ${stageLoc.y})" }
//        tutorialLabel.pack()
//        ///// TODO
    }


}

class TutorialPopup(
    private val preferences: Preferences,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    private val tutorial: Tutorial,
    skin: Skin = Scene2DSkin.defaultSkin,
) : FloatingGroup(), KGroup {

    private var tutorialLabel: Label

    init {
        this@TutorialPopup.tutorialLabel = label("", Labels.TUTORIAL.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_SMALL)
        }

        loadTutorial()
    }

    private fun loadTutorial() {
        //// TODO
        val tutorialText = tutorial.type.lines.first()
        tutorialLabel.setText(tutorialText)
        tutorialLabel.pack()
        ///// TODO


        ///// STYLE NAME
        val styleName = tutorial.orientation.style
        ////////


        ///////// OFFSET
        val offset = tutorial.offset
        //////////////

        ///// TODO
        val skin = Scene2DSkin.defaultSkin
        tutorialLabel.style = skin.get(styleName, Label.LabelStyle::class.java)
        ///// TODO


        val stageLoc = tutorial.position

        ///// TODO
        if (tutorial.orientation in listOf( Orientation.RIGHT_UP, Orientation.RIGHT_DOWN))
            stageLoc -= Vector2(tutorialLabel.width, 0f)
        tutorialLabel.setPosition(stageLoc.x, stageLoc.y)
        log.debug { "tutorialLabel.setPosition(${stageLoc.x}, ${stageLoc.y})" }
        tutorialLabel.pack()
        ///// TODO
    }


    companion object {
        private val log = logger<TutorialPopup>()
    }
}

inline fun <S> KWidget<S>.tutorialPopup(
    preferences: Preferences,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    stage: Stage,
    tutorial: Tutorial,
    init: TutorialPopup.(S) -> Unit = {}
) = actor(
    TutorialPopup(
        preferences,
        audioService,
        gameEventManager,
        tutorial
    ), init
)

