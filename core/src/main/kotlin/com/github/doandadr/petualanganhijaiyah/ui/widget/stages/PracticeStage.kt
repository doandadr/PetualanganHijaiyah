package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

class PracticeStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var harakatGroup: HorizontalGroup
    private var voiceDhommah: KImageButton
    private var voiceKasrah: KImageButton
    private var voiceFathah: KImageButton
    private val kasrahHij: Image
    private val kasrah: Image
    private val fathahHij: Image
    private val fathah: Image
    private val dhommahHij: Image
    private val dhommah: Image

    private val hijaiyahEntries = Hijaiyah.entries
    private var currentEntry: Hijaiyah = Hijaiyah.ALIF
    private val prevButton: ImageButton
    private val nextButton: ImageButton
    private val voiceButton: ImageButton
    private val hijaiyahImage: Image
    private val hijaiyahText: Label

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    init {
        this@PracticeStage.prevButton = imageButton(ImageButtons.PREVIOUS.style) {
            isTransform = true
            setOrigin(Align.center)
            setScale(SCALE_BTN_MEDIUM)
            it.padRight(-50f).align(Align.right)
        }
        container {
            toBack()
            background = skin.getDrawable(Drawables.HIJAIYAH_FRAME_LARGE.drawable)
            prefSize(215f)
            this@PracticeStage.hijaiyahImage = image {
                setScaling(Scaling.fit)
            }
            it.size(357f)
        }
        this@PracticeStage.nextButton = imageButton(ImageButtons.NEXT.style) {
            isTransform = true
            setOrigin(Align.center)
            setScale(SCALE_BTN_MEDIUM)
            it.padLeft(-50f).align(Align.left)
        }

        row()
        this@PracticeStage.voiceButton = imageButton(ImageButtons.VOICE.style) {
            it.padTop(-50f).colspan(3)
        }

        row()
        this@PracticeStage.harakatGroup = horizontalGroup {
            space(20f)
            it.spaceTop(20f).colspan(3)
            table {
                table {
                    background = skin.getDrawable(Drawables.ICONBUTTON_WHITE_ROUNDED.drawable)
                    container { this@PracticeStage.dhommah = image { setScaling(Scaling.fit) } }.prefWidth(50f)
                        .prefHeight(50f)
                    row()
                    container { this@PracticeStage.dhommahHij = image { setScaling(Scaling.fit) } }.prefWidth(100f)
                        .prefHeight(100f)
                    it.prefWidth(150f).prefHeight(200f)
                }
                row()
                this@PracticeStage.voiceDhommah = imageButton(ImageButtons.VOICE.style) { it.spaceTop(20f) }
            }
            table {
                table {
                    background = skin.getDrawable(Drawables.ICONBUTTON_WHITE_ROUNDED.drawable)
                    container { this@PracticeStage.kasrahHij = image { setScaling(Scaling.fit) } }.prefWidth(100f)
                        .prefHeight(100f)
                    row()
                    container { this@PracticeStage.kasrah = image { setScaling(Scaling.fit) } }.prefWidth(50f)
                        .prefHeight(50f)
                    it.prefWidth(150f).prefHeight(200f)
                }
                row()
                this@PracticeStage.voiceKasrah = imageButton(ImageButtons.VOICE.style) { it.spaceTop(20f) }
            }
            table {
                table {
                    background = skin.getDrawable(Drawables.ICONBUTTON_WHITE_ROUNDED.drawable)
                    container { this@PracticeStage.fathah = image { setScaling(Scaling.fit) } }.prefWidth(50f)
                        .prefHeight(50f)
                    row()
                    container { this@PracticeStage.fathahHij = image { setScaling(Scaling.fit) } }.prefWidth(100f)
                        .prefHeight(100f)
                    it.prefWidth(150f).prefHeight(200f)
                }
                row()
                this@PracticeStage.voiceFathah = imageButton(ImageButtons.VOICE.style) { it.spaceTop(20f) }
            }
        }

        row()
        this@PracticeStage.hijaiyahText = label(currentEntry.reading, Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(50f).colspan(3)
        }

        voiceButton.onChangeEvent {
            this@PracticeStage.apply {
                audioService.play(SoundAsset.CLICK_BUTTON)
                audioService.play(currentEntry.audio)
            }
        }
        voiceFathah.onChangeEvent {
            this@PracticeStage.apply {
                audioService.play(SoundAsset.CLICK_BUTTON)
                audioService.play(currentEntry.audioFathah)
            }
        }
        voiceKasrah.onChangeEvent {
            this@PracticeStage.apply {
                audioService.play(SoundAsset.CLICK_BUTTON)
                audioService.play(currentEntry.audioKasrah)
            }
        }
        voiceDhommah.onChangeEvent {
            this@PracticeStage.apply {
                audioService.play(SoundAsset.CLICK_BUTTON)
                audioService.play(currentEntry.audioDhommah)
            }
        }
        prevButton.onChangeEvent {
            this@PracticeStage.apply {
                updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) - 1])
                audioService.play(SoundAsset.CLICK_BUTTON)
            }
        }
        nextButton.onChangeEvent {
            this@PracticeStage.apply {
                updateEntry(hijaiyahEntries[hijaiyahEntries.indexOf(currentEntry) + 1])
                audioService.play(SoundAsset.CLICK_BUTTON)
            }
        }
        updateEntry(currentEntry)
    }

    private fun showTutorial() {
        Gdx.app.postRunnable {
            if (currentEntry == Hijaiyah.ALIF)
                gameEventManager.dispatchShowTutorialEvent(nextButton, TutorialType.PRACTICE_NEXT)
            if (currentEntry == Hijaiyah.BA)
                gameEventManager.dispatchShowTutorialEvent(prevButton, TutorialType.PRACTICE_PREVIOUS)
        }
    }

    private fun updateEntry(hijaiyah: Hijaiyah) {
        currentEntry = hijaiyah
        showTutorial()

        prevButton.isVisible = currentEntry != Hijaiyah.ALIF
        nextButton.isVisible = currentEntry != Hijaiyah.N10

        val isNumber = currentEntry.id.toInt() in 101..110
        harakatGroup.isVisible = !isNumber
        if (isNumber) {
            val hijDrawable = TextureRegionDrawable(textAtlas.findRegion(currentEntry.imageNumber))
            hijaiyahImage.drawable = hijDrawable
            hijaiyahText.setText(currentEntry.reading.uppercase())
            return
        }

        val hijDrawable = TextureRegionDrawable(textAtlas.findRegion(currentEntry.image))
        val fathahDrawable = TextureRegionDrawable(textAtlas.findRegion(Harakat.FATHAH.image))
        val kasrahDrawable = TextureRegionDrawable(textAtlas.findRegion(Harakat.KASRAH.image))
        val dhommahDrawable = TextureRegionDrawable(textAtlas.findRegion(Harakat.DHOMMAH.image))
        hijaiyahImage.drawable = hijDrawable
        hijaiyahText.setText(currentEntry.reading.uppercase())

        // Harakat
        fathahHij.drawable = hijDrawable
        kasrahHij.drawable = hijDrawable
        dhommahHij.drawable = hijDrawable
        fathah.drawable = fathahDrawable
        kasrah.drawable = kasrahDrawable
        dhommah.drawable = dhommahDrawable
    }
}

inline fun <S> KWidget<S>.practiceStage(
    assets: AssetStorage,
    audioService: AudioService,
    gameEventManager: GameEventManager,
    init: PracticeStage.(S) -> Unit = {}
) = actor(
    PracticeStage(
        assets,
        audioService,
        gameEventManager,
    ), init
)
