package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextButtons
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_BETWEEN_BUTTONS
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*


class MCQJoin(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    // TODO RTL functionality
    private var vertGroup: VerticalGroup
    private val answerLabel: Label
    private val hijaiyahEntries = Hijaiyah.entries
    private val choiceBoxes = mutableListOf<TextButton>()
    var currentEntries: List<Hijaiyah> = pickRandomEntries(ENTRY_COUNT)
    private var correctAnswer: Hijaiyah = currentEntries.random()
    private val skipButton: ImageTextButton

    init {
        background = skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable)

        this@MCQJoin.answerLabel = label(correctAnswer.name.uppercase(), Labels.TEXTBOX_WHITE_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_MEDIUM)
            it.padTop(PADDING_INNER_SCREEN)
        }

        row()
        this@MCQJoin.vertGroup = verticalGroup {
            space(SPACE_BETWEEN_BUTTONS)
            it.spaceTop(50f)
        }

        row()
        this@MCQJoin.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@MCQJoin.loadStage()
            }
            it.spaceTop(50f).padBottom(PADDING_INNER_SCREEN)
        }

        loadStage()
    }

    fun handleAnswer(index: Int) {
        if (currentEntries[index] == correctAnswer) {
            // TODO handle correct
            choiceBoxes[index].apply {
                style = skin.get(TextButtons.JOIN_STATE.style, TextButtonStyle::class.java)
                isDisabled = false
            }
        } else {
            // TODO handle incorrect
            choiceBoxes[index].apply {
                style = skin.get(TextButtons.JOIN_STATE.style, TextButtonStyle::class.java)
                isDisabled = true
            }
        }
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {
        currentEntries = pickRandomEntries(ENTRY_COUNT)
        correctAnswer = currentEntries.random()
        choiceBoxes.clear()

        vertGroup.clearChildren()
        currentEntries.forEachIndexed { index, letter ->
            val box = TextButton(letter.name.uppercase(), skin, TextButtons.JOIN.style)
            box.onChangeEvent {
                this@MCQJoin.handleAnswer(index)
            }
            choiceBoxes += box
            vertGroup.addActor(choiceBoxes[index])
        }

        answerLabel.setText(correctAnswer.name.uppercase())
    }

    companion object {
        private const val ENTRY_COUNT = 3

        private val log = logger<MCQJoin>()
    }
}

inline fun <S> KWidget<S>.mcqJoin(
    assets: AssetStorage,
    audioService: AudioService,
    init: MCQJoin.(S) -> Unit = {}
) = actor(
    MCQJoin(
        assets,
        audioService
    ), init
)
