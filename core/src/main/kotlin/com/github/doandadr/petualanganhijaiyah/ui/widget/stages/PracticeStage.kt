package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

// TODO fix layout
class PracticeStage(
    assets: AssetStorage,
    val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private val hijaiyahEntries = Hijaiyah.entries
    var currentEntry: Hijaiyah = Hijaiyah.ALIF
    val prevButton: ImageButton
    val nextButton: ImageButton
    val voiceButton: ImageButton
    val hijaiyahImage: Image
    val hijaiyahText: Label

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    init {
        this@PracticeStage.prevButton = imageButton(ImageButtons.PREVIOUS.style) {
            toFront()
            it.padRight(-50f)
        }

        container {
            background = skin.getDrawable(Drawables.HIJAIYAH_FRAME_LARGE.drawable)
            prefSize(215f)
            this@PracticeStage.hijaiyahImage = image(this@PracticeStage.currentEntry.image) {
                setScaling(Scaling.fit)
            }
        }

        this@PracticeStage.nextButton = imageButton(ImageButtons.NEXT.style) {
            toFront()
            it.padLeft(-50f)
        }
        row()

        this@PracticeStage.voiceButton = imageButton(ImageButtons.VOICE.style) {
            toFront()
            it.padTop(-50f).colspan(3)
        }
        row()

        this@PracticeStage.hijaiyahText = label(currentEntry.name, Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(50f).colspan(3)
        }

        pack()

        voiceButton.onChangeEvent {
            this@PracticeStage.apply {
                audioService.play(currentEntry.audio)
            }
        }

        prevButton.onChangeEvent {
            this@PracticeStage.apply {
                updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) - 1])
            }
        }
        nextButton.onChangeEvent {
            this@PracticeStage.apply {
                updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) + 1])
            }
        }


        updateEntry(currentEntry)
    }

    private fun updateEntry(hijaiyah: Hijaiyah) {
        currentEntry = hijaiyah
        hijaiyahImage.drawable = TextureRegionDrawable(textAtlas.findRegion(currentEntry.image))
        hijaiyahText.setText(currentEntry.name.uppercase())

        prevButton.isVisible = currentEntry != Hijaiyah.ALIF
        nextButton.isVisible = currentEntry != Hijaiyah.YA

    }
}

inline fun <S> KWidget<S>.practiceStage(
    assets: AssetStorage,
    audioService: AudioService,
    init: PracticeStage.(S) -> Unit = {}
) = actor(
    PracticeStage(
        assets,
        audioService
    ), init
)
