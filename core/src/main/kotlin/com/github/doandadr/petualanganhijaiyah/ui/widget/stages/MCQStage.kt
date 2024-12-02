package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class MCQStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable{
    private val hijaiyahEntries = Hijaiyah.entries
    private lateinit var currentEntries: List<Hijaiyah>
    private val choiceBoxes = mutableListOf<HijaiyahBox>()
    private lateinit var correctAnswer: Hijaiyah

    private var horiGroup: HorizontalGroup
    private val answerLabel: Label
    private val skipButton: ImageTextButton

    init {
        background = skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable)

        this@MCQStage.horiGroup = horizontalGroup {
            space(SPACE_HIJAIYAH_MEDIUM)
            it.padTop(PADDING_INNER_SCREEN)
        }

        row()
        label("Yang manakah...", Labels.SECONDARY_BORDER.style) {
            it.spaceTop(50f)
        }

        row()
        this@MCQStage.answerLabel = label("", Labels.TEXTBOX_WHITE_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_MEDIUM)
            it.spaceTop(20f)
        }

        row()
        this@MCQStage.skipButton = imageTextButton("   Lewati",ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@MCQStage.loadStage()
            }
            it.spaceTop(50f).padBottom(PADDING_INNER_SCREEN)
        }

        loadStage()
    }

     fun handleAnswer(index: Int) {
        if (currentEntries[index] == correctAnswer) {
            // TODO handle correct

            choiceBoxes[index].setState(HijaiyahBox.State.CORRECT)
        } else {
            // TODO handle incorrect

            choiceBoxes[index].setState(HijaiyahBox.State.INCORRECT)
        }
    }

    fun pickRandomEntries(count: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(count)

    fun loadStage() {
        // get random letters
        currentEntries = pickRandomEntries(ENTRY_COUNT)
        // set correct answer
        correctAnswer = currentEntries.random()
        // clear choice boxes
        choiceBoxes.clear()

        // foreach random letters
        // add boxes with listeners
        horiGroup.clearChildren()
        currentEntries.forEachIndexed { index, letter ->
            choiceBoxes += HijaiyahBox(letter, HijaiyahBox.Size.MEDIUM, assets)
            choiceBoxes[index].onChangeEvent {
                this@MCQStage.handleAnswer(index)
            }
            horiGroup.addActor(choiceBoxes[index])
        }

        answerLabel.setText(correctAnswer.name.uppercase())
    }

    companion object {
        private const val ENTRY_COUNT = 3

        private val log = logger<MCQStage>()
    }
}

inline fun <S> KWidget<S>.mcqStage(
    assets: AssetStorage,
    audioService: AudioService,
    init: MCQStage.(S) -> Unit = {}
) = actor(
    MCQStage(
        assets,
        audioService
    ), init
)
