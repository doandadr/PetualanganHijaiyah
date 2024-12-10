package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_DRAWING_BOARD
import ktx.actors.onChangeEvent
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
    private lateinit var drawImage: Image
    private lateinit var incorrectButton: Button
    private lateinit var correctButton: Button
    private var skipButton: KImageTextButton
    private var hijaiyahText: Label
    private val hijaiyahEntries = Hijaiyah.entries
    private val drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))
    private lateinit var currentEntry : Hijaiyah

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

    private val points = mutableListOf<Vector2>()

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
            this@DrawingStage.drawImage = image()
        }.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                points.clear()
                points.add(Vector2(x, y))
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                points.add(Vector2(x, y))
            }
        })

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

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {
        currentEntry = pickRandomEntries(1).first()
        hijaiyahText.setText(currentEntry.name)
        points.clear()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        drawer.update()

        if (points.isNotEmpty()) {
            for (i in 0 until points.size - 1) {
                if (points.isNotEmpty()) {
                    for (i in 0 until points.size - 1) {
                        drawer.line(points[i], points[i + 1], 10f) // Adjust the line thickness as needed
                    }
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
