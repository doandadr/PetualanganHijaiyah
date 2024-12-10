package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import com.github.doandadr.petualanganhijaiyah.ui.widget.hijaiyahBox
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class MCQStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    private var answerBox: Stack
    private val hijaiyahEntries = Hijaiyah.entries
    private lateinit var currentEntries: List<Hijaiyah>
    private val choiceBoxes = mutableListOf<HijaiyahBox>()
    private lateinit var correctAnswer: Hijaiyah

    private var horiGroup: HorizontalGroup
    private val answerLatin: Label
    private var answerArabic: HijaiyahBox
    private val skipButton: ImageTextButton
    private var state: State = State.LATIN

    init {
        background = skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable)

        label("Yang manakah...", Labels.SECONDARY_BORDER.style) {
            color = skin.getColor(Colors.ORANGE.color)
            it.padTop(PADDING_INNER_SCREEN)
        }

        row()
        this@MCQStage.answerBox = stack {
            this@MCQStage.answerLatin = label("", Labels.TEXTBOX_WHITE_SQUARE_LARGE.style) {
                setAlignment(Align.center)
                setFontScale(SCALE_FONT_MEDIUM)
            }
            this@MCQStage.answerArabic = hijaiyahBox(Hijaiyah.ALIF, HijaiyahBox.Size.MEDIUM, this@MCQStage.assets) {
                touchable = Touchable.disabled
            }
            it.spaceTop(30f)
        }

        row()
        this@MCQStage.horiGroup = horizontalGroup {
            space(SPACE_HIJAIYAH_MEDIUM)
            it.spaceTop(100f)
        }

        row()
        this@MCQStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@MCQStage.loadStage()
            }
            it.spaceTop(50f).padBottom(PADDING_INNER_SCREEN)
        }

        loadStage()
    }

    private fun handleAnswer(index: Int) {
        if (currentEntries[index] == correctAnswer) {
            choiceBoxes[index].setState(HijaiyahBox.State.CORRECT)
            audioService.play(choiceBoxes[index].hijaiyah.audio)
            gameEventManager.dispatchAnswerCorrectEvent(true)
        } else {
            choiceBoxes[index].setState(HijaiyahBox.State.INCORRECT)
            audioService.play(choiceBoxes[index].hijaiyah.audio)
            gameEventManager.dispatchAnswerIncorrectEvent(true)
        }
    }

    private fun pickRandomEntries(count: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(count)

    private fun loadStage() {
        // ramdomize whether stage is arabic to latin or vice versa
        state = State.entries.random()
        // get random letters
        currentEntries = pickRandomEntries(ENTRY_COUNT)
        // set correct answer
        correctAnswer = currentEntries.random()
        // clear choice boxes
        choiceBoxes.clear()

        // for each random letters
        // add boxes with listeners
        horiGroup.clearChildren()
        currentEntries.forEachIndexed { index, letter ->
            val box = HijaiyahBox(letter, HijaiyahBox.Size.MEDIUM, assets)
            box.setType(if (state == State.ARABIC) HijaiyahBox.Type.DEFAULT else HijaiyahBox.Type.TEXT)
            box.onChangeEvent {
                this@MCQStage.handleAnswer(index)
            }

            choiceBoxes += box
            horiGroup.addActor(box)
        }
        answerArabic.isVisible = state == State.LATIN
        answerLatin.isVisible = state == State.ARABIC

        answerLatin.setText(correctAnswer.name.uppercase())
        answerArabic.updateLetter(correctAnswer)
    }

    enum class State {
        ARABIC,
        LATIN
    }

    companion object {
        private const val ENTRY_COUNT = 3

        private val log = logger<MCQStage>()
    }
}

inline fun <S> KWidget<S>.mcqStage(
    assets: AssetStorage,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    init: MCQStage.(S) -> Unit = {}
) = actor(
    MCQStage(
        assets,
        audioService,
        gameEventManager,
    ), init
)
