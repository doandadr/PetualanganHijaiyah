package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_DRAWING_BOARD
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import ktx.actors.onChange
import ktx.actors.onChangeEvent
import ktx.actors.onTouchDown
import ktx.actors.plusAssign
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*
import space.earlygrey.shapedrawer.ShapeDrawer


class DrawingStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val batch: Batch,
    private val gameEventManager: GameEventManager,
    private val skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var submitButton: ImageTextButton
    private var drawingBoard: Table
    private var drawImage: Image
    private var incorrectButton: Button
    private var correctButton: Button
    private var skipButton: ImageButton
    private var hijaiyahText: Label
    private val hijaiyahEntries = Hijaiyah.entries
    private val drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))
    private lateinit var currentEntry: Hijaiyah

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    // List to store the points of the drawing
    private val segments = mutableListOf<MutableList<Vector2>>()


    init {
        background(skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable))

        label("Tuliskan huruf...", Labels.SECONDARY_BORDER.style) {
            it.padTop(PADDING_INNER_SCREEN).colspan(2)
        }

        row()
        this@DrawingStage.hijaiyahText = label("", Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(20f).colspan(2)
        }

        row()
        this@DrawingStage.drawingBoard = table {
            background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
            touchable = Touchable.enabled
            it.prefSize(SIZE_DRAWING_BOARD).spaceTop(30f).colspan(2)

            this@DrawingStage.drawImage = image()
        }

        row()
        horizontalGroup {
            this@DrawingStage.correctButton = button(Buttons.CHECK.style)
            this@DrawingStage.incorrectButton = button(Buttons.X.style)
            it.colspan(2)
        }

        row()
        this@DrawingStage.submitButton = imageTextButton("   Jawab", ImageTextButtons.SUBMIT.style) {
            isTransform = true
            setOrigin(Align.center)
            setScale(SCALE_BTN_SMALL)
            onTouchDown {
                this.clearActions()
                this += Animations.pulseAnimation()
            }
            onChange {
                this@DrawingStage.handleSubmission()
            }
            it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expandY()
        }

        this@DrawingStage.skipButton = imageButton(ImageButtons.SKIP.style) {
            isTransform = true
            setOrigin(Align.bottom)
            setScale(SCALE_BTN_SMALL)
            onChangeEvent {
                this@DrawingStage.loadStage()
            }
            it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expandY()
        }

        loadStage()
        setTutorials()
    }

    private fun setTutorials() {
        Gdx.app.postRunnable {
            gameEventManager.dispatchShowTutorialEvent(drawingBoard, TutorialType.DRAW_START)
        }
    }

    private fun handleSubmission() {
        // TODO capture image in middle of table
        // TODO send image into ML
        // Reset image

        segments.clear()
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    private fun loadStage() {
        currentEntry = pickRandomEntries(1).first()
        hijaiyahText.setText(currentEntry.name)
        segments.clear()

        correctButton.onChange {
            gameEventManager.dispatchAnswerCorrectEvent(true)
        }
        incorrectButton.onChange {
            gameEventManager.dispatchAnswerIncorrectEvent(true)
        }

        drawingBoard.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                segments.add(mutableListOf(drawingBoard.localToStageCoordinates(Vector2(x, y))))
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                segments.last().add(drawingBoard.localToStageCoordinates(Vector2(x, y)))
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })

//        drawImage.drawable = skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable)
//        drawImage.touchable = Touchable.disabled
//        drawImage.setOrigin(Align.center)
//        drawImage.setScaling(Scaling.stretch)
//        drawImage.setScale(1.5f)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        drawer.update()

        // Draw each segment
        for (segment in segments) {
            if (segment.isNotEmpty()) {
                for (i in 0 until segment.size - 1) {
                    drawer.line(segment[i], segment[i + 1], 10f) // Adjust the line thickness as needed
                }
            }
        }
    }

    companion object {
        private val log = logger<DragAndDropStage>()
    }
}

inline fun <S> KWidget<S>.drawingStage(
    assets: AssetStorage,
    audioService: AudioService,
    batch: Batch,
    gameEventManager: GameEventManager,
    init: DrawingStage.(S) -> Unit = {}
) = actor(
    DrawingStage(
        assets,
        audioService,
        batch,
        gameEventManager,
    ), init
)
