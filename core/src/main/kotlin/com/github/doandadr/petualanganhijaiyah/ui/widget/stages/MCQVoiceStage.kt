package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageButtons
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class MCQVoiceStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var answerVoiceButton: ImageButton
    private var horiGroup: HorizontalGroup
    private val hijaiyahEntries = Hijaiyah.entries
    private val choiceBoxes = mutableListOf<HijaiyahBox>()
    private lateinit var currentLetters: List<Hijaiyah>
    private lateinit var correctAnswer: Hijaiyah
    private val skipButton: ImageTextButton

    init {
        background = skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable)

        this@MCQVoiceStage.horiGroup = horizontalGroup {
            space(SPACE_HIJAIYAH_MEDIUM)
            it.padTop(PADDING_INNER_SCREEN)
        }

        row()
        label("Yang manakah...", Labels.SECONDARY_BORDER.style) {
            it.spaceTop(50f)
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
        this@MCQVoiceStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@MCQVoiceStage.loadStage()
            }
            it.spaceTop(50f).padBottom(PADDING_INNER_SCREEN)
        }

        loadStage()
    }

    private fun handleAnswer(index: Int) {
        if (currentLetters[index] == correctAnswer) {
            // TODO handle correct

            choiceBoxes[index].setState(HijaiyahBox.State.CORRECT)
        } else {
            // TODO handle incorrect

            choiceBoxes[index].setState(HijaiyahBox.State.INCORRECT)
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
    init: MCQVoiceStage.(S) -> Unit = {}
) = actor(
    MCQVoiceStage(
        assets,
        audioService
    ), init
)