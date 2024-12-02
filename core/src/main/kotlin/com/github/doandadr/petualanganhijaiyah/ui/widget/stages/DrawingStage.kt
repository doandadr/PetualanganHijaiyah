package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_DRAWING_BOARD
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*


class DrawingStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var skipButton: KImageTextButton
    private var hijaiyahText: Label
    private val hijaiyahEntries = Hijaiyah.entries
    private lateinit var currentEntry : Hijaiyah

    init {
        background(skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable))

        label("Tuliskan huruf...", Labels.SECONDARY_BORDER.style) {
            it.padTop(PADDING_INNER_SCREEN)
        }

        row()
        this@DrawingStage.hijaiyahText = label("", Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(20f)
        }

        row()
        table {
            background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
            it.prefSize(SIZE_DRAWING_BOARD).spaceTop(30f)
        }


        row()
        this@DrawingStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            isTransform = true
            setOrigin(Align.bottom)
            setScale(SCALE_BTN_SMALL)
            onChangeEvent {
                this@DrawingStage.loadStage()
            }
            it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expand()
        }

        loadStage()
    }

    private fun pickRandomEntries(amount: Int): kotlin.collections.List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {
        currentEntry = pickRandomEntries(1).first()

        hijaiyahText.setText(currentEntry.name)


        val dragAndDrop = DragAndDrop()
//        dragAndDrop.addSource()
    }

    fun handleAnswer(index: Int) {

    }

    companion object {
        private val log = logger<DragAndDropStage>()
    }
}

inline fun <S> KWidget<S>.drawingStage(
    assets: AssetStorage,
    audioService: AudioService,
    init: DrawingStage.(S) -> Unit = {}
) = actor(
    DrawingStage(
        assets,
        audioService
    ), init
)
