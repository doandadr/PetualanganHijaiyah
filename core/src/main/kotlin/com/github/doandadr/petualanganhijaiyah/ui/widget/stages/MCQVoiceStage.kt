package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class MCQVoiceStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var answerVoiceButton: ImageButton
    private var horiGroup: HorizontalGroup
    private val hijaiyahEntries = Hijaiyah.entries.take(28)
    private val choiceBoxes = mutableListOf<HijaiyahBox>()
    private lateinit var currentLetters: List<Hijaiyah>
    private lateinit var correctAnswer: Hijaiyah
    private val skipButton: ImageTextButton

    init {
        background = skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable)

        label("Yang manakah...", Labels.SECONDARY_BORDER.style) {
            it.padTop(200f)
        }

        row()
        this@MCQVoiceStage.answerVoiceButton = imageButton(ImageButtons.VOICE.style) {
            onChangeEvent {
                this@MCQVoiceStage.apply {
                    audioService.play(correctAnswer.audio)
                }
            }
            it.spaceTop(20f)
        }

        row()
        this@MCQVoiceStage.horiGroup = horizontalGroup {
            space(SPACE_HIJAIYAH_MEDIUM)
            it.spaceTop(50f).expand()
        }

        row()
        this@MCQVoiceStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@MCQVoiceStage.loadStage()
            }
            it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expand()
        }

        loadStage()
        setTutorials()
    }

    private fun setTutorials() {
        Gdx.app.postRunnable{
            gameEventManager.dispatchShowTutorialEvent(answerVoiceButton, TutorialType.VOICE_START)
            gameEventManager.dispatchShowTutorialEvent(horiGroup.children.first(), TutorialType.VOICE_OPTION)
        }
    }

    private fun handleAnswer(index: Int) {
        if (currentLetters[index] == correctAnswer) {
            choiceBoxes[index].setState(HijaiyahBox.State.CORRECT)
            audioService.play(choiceBoxes[index].hijaiyah.audio)
            gameEventManager.dispatchAnswerCorrectEvent(true)
        } else {
            choiceBoxes[index].setState(HijaiyahBox.State.INCORRECT)
            audioService.play(choiceBoxes[index].hijaiyah.audio)
            gameEventManager.dispatchAnswerIncorrectEvent(true)
        }
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    private fun loadStage() {
        // get random letters
        currentLetters = pickRandomEntries(ENTRY_COUNT)
        // set correct answer
        correctAnswer = currentLetters.random()
        // clear choice boxes
        choiceBoxes.clear()

        // foreach random letters
        // add boxes with listeners
        horiGroup.clearChildren()
        currentLetters.forEachIndexed { index, letter ->
            choiceBoxes += HijaiyahBox(letter, HijaiyahBox.Size.MEDIUM, assets)
            choiceBoxes[index].onChangeEvent {
                this@MCQVoiceStage.handleAnswer(index)
            }
            horiGroup.addActor(choiceBoxes[index])
        }
    }


    companion object {
        private const val ENTRY_COUNT = 3

        private val log = logger<MCQVoiceStage>()
    }
}

inline fun <S> KWidget<S>.mcqVoiceStage(
    assets: AssetStorage,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    init: MCQVoiceStage.(S) -> Unit = {}
) = actor(
    MCQVoiceStage(
        assets,
        audioService,
        gameEventManager,
    ), init
)
