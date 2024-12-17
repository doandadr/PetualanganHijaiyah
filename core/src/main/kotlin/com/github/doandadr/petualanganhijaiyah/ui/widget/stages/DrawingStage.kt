package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Timer
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ml.PreProcessHelper
import com.github.doandadr.petualanganhijaiyah.ml.TensorFlowUtils
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_DRAWING_BOARD
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import ktx.actors.onChange
import ktx.actors.onTouchDown
import ktx.actors.plusAssign
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*
import org.tensorflow.SavedModelBundle
import space.earlygrey.shapedrawer.ShapeDrawer
import space.earlygrey.shapedrawer.scene2d.ShapeDrawerDrawable


class DrawingStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val batch: Batch,
    private val gameEventManager: GameEventManager,
    private val mlModel: SavedModelBundle,
    private val skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var drawArea: Image
    private var resetButton: ImageButton
    private var submitButton: ImageTextButton
    private var drawingBoard: Table

    private var incorrectButton: Button
    private var correctButton: Button
    private var skipButton: ImageButton
    private var hijaiyahText: Label
    private val hijaiyahEntries = Hijaiyah.entries
    private lateinit var currentEntry: Hijaiyah

    private var drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    // List to store the points of the drawing
    private val segments = mutableListOf<MutableList<Vector2>>()

    //    private var boardRect: List<Float>? = null
//    private var drawRect: List<Float>? = null
    private var drawNow: Boolean = false

    init {
        initShapeDrawer()

        background(skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable))

        label("Tuliskan huruf...", Labels.SECONDARY_BORDER.style) {
            it.padTop(PADDING_INNER_SCREEN).colspan(3)
        }

        row()
        this@DrawingStage.hijaiyahText = label("", Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(20f).colspan(3)
        }

        row()
        this@DrawingStage.drawingBoard = table {
            it.padLeft(PADDING_INNER_SCREEN).padRight(PADDING_INNER_SCREEN).size(SIZE_DRAWING_BOARD).spaceTop(30f)
                .colspan(3)
            background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
            touchable = Touchable.enabled

            this@DrawingStage.drawArea = image(object : ShapeDrawerDrawable(this@DrawingStage.drawer) {
                override fun drawShapes(shapeDrawer: ShapeDrawer?, x: Float, y: Float, width: Float, height: Float) {
                    this@DrawingStage.drawer.update()

                    // Draw each segment
                    for (segment in this@DrawingStage.segments) {
                        if (segment.isNotEmpty()) {
                            for (i in 0 until segment.size - 1) {
                                this@DrawingStage.drawer.filledCircle(segment[i].x, segment[i].y, 8f, Color.BLACK)
                                this@DrawingStage.drawer.line(segment[i], segment[i + 1], Color.BLACK, 16f)
                            }
                        }
                    }
                    this@DrawingStage.drawImageFromInput()
                }

            })
        }

        row()
        horizontalGroup {
            it.colspan(3)
            this@DrawingStage.correctButton = button(Buttons.CHECK.style)
            this@DrawingStage.incorrectButton = button(Buttons.X.style)
        }

        row()
        horizontalGroup {
            it.padBottom(PADDING_INNER_SCREEN).expand().align(Align.bottom)

            this@DrawingStage.resetButton = imageButton(ImageButtons.REPEAT.style) {
                setScale(SCALE_BTN_SMALL)
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    setScale(SCALE_BTN_SMALL)
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.resetDrawingBoard()
                }
            }
            this@DrawingStage.skipButton = imageButton(ImageButtons.SKIP.style) {
                setScale(SCALE_BTN_SMALL)
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    setScale(SCALE_BTN_SMALL)
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.loadStage()
                }
            }
            this@DrawingStage.submitButton = imageTextButton("   Jawab", ImageTextButtons.SUBMIT.style) {
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.handleSubmission()
                }
            }
        }

        loadStage()
        setTutorials()
    }

    private fun initShapeDrawer() {
        // Create a 1x1 white texture
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        val whiteTexture = TextureRegion(Texture(pixmap))
        pixmap.dispose()
        // Initialize ShapeDrawer
        drawer = ShapeDrawer(batch, whiteTexture)
    }

    lateinit var boardPos: Vector2
    lateinit var preProcessHelper: PreProcessHelper
    lateinit var resultImageArray: Array<FloatArray>

    private fun drawImageFromInput() {
        if (drawNow) {

            val imageSize = 64
            val pixelSize = 10f // Size of each pixel when drawn
//            val input = TensorFlowUtils.normalizeAndReshape(image1DArray, 64, 64)

            for (y in 0 until imageSize) {
                for (x in 0 until imageSize) {
//                    val pixelValue = input[y][x]
                    val pixelValue = resultImageArray[y][x]
                    val color = Color(pixelValue, pixelValue, pixelValue, 1f)
                    this@DrawingStage.drawer.setColor(color)
                    this@DrawingStage.drawer.filledRectangle(
                        boardPos.x + x * pixelSize,
                        boardPos.y + (imageSize - 1 - y) * pixelSize, pixelSize, pixelSize
                    )
                }
            }
        }
    }

    private fun resetDrawingBoard() {
        segments.clear()
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

        boardPos = drawingBoard.localToStageCoordinates(Vector2())
        preProcessHelper = PreProcessHelper(segments, boardPos, SIZE_DRAWING_BOARD)
        resultImageArray = preProcessHelper.getInputArray()

        val prediction = TensorFlowUtils.predict(mlModel, resultImageArray)
        log.debug { "Predicted: $prediction, ${hijaiyahEntries.find { it.id == prediction }}" }

        segments.clear()
        drawNow = true
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                drawNow = false
            }
        }, 3f)

//        TensorFlowUtils.convertValuesToImage(TensorFlowUtils.normalize(image1DArray), true)
    }


    private fun loadStage() {
        currentEntry = pickRandomEntries(1).first()
        hijaiyahText.setText(currentEntry.reading.uppercase())
        segments.clear()

        correctButton.onChange {
            gameEventManager.dispatchAnswerCorrectEvent(true)
        }
        incorrectButton.onChange {
            gameEventManager.dispatchAnswerIncorrectEvent(true)
        }

        drawingBoard.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val coords = drawingBoard.localToStageCoordinates(Vector2(x, y))
                segments.add(mutableListOf(coords, coords))
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                segments.last().add(drawingBoard.localToStageCoordinates(Vector2(x, y)))
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    companion object {
        private val log = logger<DragAndDropStage>()
    }
}

inline fun <S> KWidget<S>.drawingStage(
    assets: AssetStorage,
    audioService: AudioService,
    batch: Batch,
    gameEventManager: GameEventManager,
    mlModel: SavedModelBundle,
    init: DrawingStage.(S) -> Unit = {}
) = actor(
    DrawingStage(
        assets,
        audioService,
        batch,
        gameEventManager,
        mlModel,
    ), init
)
