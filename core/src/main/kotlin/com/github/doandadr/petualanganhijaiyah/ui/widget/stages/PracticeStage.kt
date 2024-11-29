package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.Colors
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import ktx.actors.onChange
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
    private val prevButton: Button
    private val nextButton: Button
    val voiceButton: Button
    val leftImage: Image
    val rightLabel: Label

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    init {
        debug = true
        background = skin.getDrawable(Drawables.BOX.drawable)

        horizontalGroup {
            space(30f)

            // TODO resize drawables to not be as big, imgTButton and fxx files OR try to fix the alignment issues
            stack {
                isTransform = true
                setOrigin(Align.center)
                setScale(0.7f)
                imageTextButton("")
                this@PracticeStage.leftImage = image(Hijaiyah.ALIF.image) {
                    setScaling(Scaling.none)
                }
            }

            image(Drawables.SLIDER_KNOB_G.drawable)

            stack {
                isTransform = true
                setOrigin(Align.center)
                setScale(0.7f)
                imageTextButton("")
                this@PracticeStage.rightLabel = label("ALIF") {
                    setAlignment(Align.center)
                    color = skin.getColor(Colors.BLACK.color)
                    setFontScale(3f)
                }
            }
        }
        row()

        this@PracticeStage.voiceButton = button(Buttons.VOICE.style)
        row()

        horizontalGroup {
            space(60f)
            this@PracticeStage.prevButton = button(Buttons.LEFT.style)
            this@PracticeStage.nextButton = button(Buttons.RIGHT.style)
        }

        voiceButton.onChange {
            this@PracticeStage.apply {
                audioService.play(currentEntry.audio)
            }
        }

        prevButton.onChange {
            updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) - 1])
        }
        nextButton.onChange {
            updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) + 1])
        }

        updateEntry(currentEntry)
    }

    private fun updateEntry(hijaiyah: Hijaiyah) {
        currentEntry = hijaiyah
        leftImage.drawable = TextureRegionDrawable(textAtlas.findRegion(currentEntry.image))
        rightLabel.setText(currentEntry.name.uppercase())

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
