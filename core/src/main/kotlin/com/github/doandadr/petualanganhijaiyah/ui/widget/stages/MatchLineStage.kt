package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.graphics.Color.BLACK
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import com.github.doandadr.petualanganhijaiyah.ui.widget.MatchBox
import ktx.actors.alpha
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*
import space.earlygrey.shapedrawer.ShapeDrawer
import space.earlygrey.shapedrawer.scene2d.ShapeDrawerDrawable

class MatchLineStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val batch: Batch,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var drawArea: Image
    private val hijaiyahEntries = Hijaiyah.entries
    lateinit var leftEntries: List<Hijaiyah>
    lateinit var rightEntries: List<Hijaiyah>

    private val leftGroup: VerticalGroup
    private val rightGroup: VerticalGroup
    private val skipButton: ImageTextButton
    private val dragAndDrop = DragAndDrop()

    private val drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))

    private val savedLines: MutableList<Line> = mutableListOf()

    init {
        background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
        horizontalGroup {
            space(150f)
            this@MatchLineStage.leftGroup = verticalGroup {
                space(40f)
            }

            this@MatchLineStage.rightGroup = verticalGroup {
                space(40f)
            }
            it.padTop(PADDING_INNER_SCREEN).expand()
        }

        row()
        this@MatchLineStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            isTransform = true
            setOrigin(Align.bottom)
            setScale(SCALE_BTN_SMALL)
            onChangeEvent {
                this@MatchLineStage.loadStage()
            }
            it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expand()
        }

        row()
        this@MatchLineStage.drawArea = image(object : ShapeDrawerDrawable(drawer) {
            override fun drawShapes(shapeDrawer: ShapeDrawer?, x: Float, y: Float, width: Float, height: Float) {
                drawer.line(0f, 0f, 100f, 100f, BLACK, 10f)
                drawer.update()
            }
        })
        {
            setFillParent(true)
            color = skin.getColor(Colors.BLACK.color)
            toFront()
            it.grow().align(Align.bottomLeft)
        }

        loadStage()
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {

        leftGroup.clearChildren()
        leftEntries = pickRandomEntries(5)
        leftEntries.shuffled().forEachIndexed { _, hijaiyah ->
            val box = MatchBox(hijaiyah, MatchBox.State.LEFT, assets)
            box.rightDot.userObject = box
            box.onChangeEvent {
                log.debug { "right box touched" }
            }

            addDragSource(box)
            addDragTarget(box)
            leftGroup.addActor(box)
        }

        rightGroup.clearChildren()
        rightEntries = leftEntries.shuffled()
        rightEntries.shuffled().forEachIndexed { index, hijaiyah ->
            val box = MatchBox(hijaiyah, MatchBox.State.RIGHT, assets)
            box.box.setType(HijaiyahBox.Type.TEXT)
            box.onChangeEvent {
                log.debug { "right box touched" }
            }
            box.leftDot.userObject = box

            addDropTarget(box)
            rightGroup.addActor(box)
        }
    }

    private fun addDragSource(box: MatchBox) {
        log.debug { "Adding drag source" }
        dragAndDrop.addSource(object : DragAndDrop.Source(box) {
            override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                log.debug { "Start dragging source" }
                val payload: DragAndDrop.Payload = DragAndDrop.Payload()
                val draggedDot = (actor as MatchBox).rightDot
                draggedDot.alpha = 1f
                payload.dragActor = draggedDot
                stage.addActor(draggedDot)
                dragAndDrop.setDragActorPosition(draggedDot.width / 2, -draggedDot.height / 2)
                audioService.play(SoundAsset.STRETCH)

                return payload
            }

            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                log.debug { "Dragging source" }
                super.drag(event, x, y, pointer)
                // TODO draw a line from actor.userObject.leftCircle to current location
                drawLines()
            }

            override fun dragStop(
                event: InputEvent?,
                x: Float,
                y: Float,
                pointer: Int,
                payload: DragAndDrop.Payload?,
                target: DragAndDrop.Target?
            ) {
                log.debug { "Stop dragging source" }

                val sourceBox = box

                if (payload != null) {
                    if (target == null) {
                        sourceBox.rightCircle.actor = payload.dragActor
                        audioService.play(SoundAsset.CANCEL)
                    } else if ((target.actor as MatchBox).hijaiyah != sourceBox.hijaiyah) {
                        sourceBox.rightCircle.actor = payload.dragActor
                        audioService.play(SoundAsset.INCORRECT)
                        // TODO handle incorrect
                    }
                }


            }
        })
    }

    private fun drawLines() {

    }

    private fun addDragTarget(frame: MatchBox) {

        log.debug { "Adding drag target" }
        dragAndDrop.addTarget(object :DragAndDrop.Target(frame) {
            override fun drag(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ): Boolean {
                log.debug { "Dragging payload over drag target" }
                // TODO animate scale up leftCircle
                return true
            }

            override fun drop(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ) {
                log.debug { "Dropping payload on drag target" }
                if (payload != null) {
                    val payloadDot = payload.dragActor as Image

                    if (frame.rightCircle.actor == null) {
                        frame.rightCircle.actor = payloadDot
                        audioService.play(SoundAsset.DROP)
                    } else if (frame.hijaiyah == (payloadDot.userObject as MatchBox).hijaiyah) {
                        frame.rightCircle.actor = payloadDot
                    }
                }
            }

        })
    }

    private fun addDropTarget(frame: MatchBox) {
        log.debug { "Adding drop target" }
        dragAndDrop.addTarget(object : DragAndDrop.Target(frame) {
            override fun drag(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ): Boolean {
                log.debug { "Dragging payload over drop target" }
                // TODO animate scale up rightCircle
                return true
            }

            override fun drop(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ) {
                log.debug { "Dropping payload on drop target" }
                if (payload != null) {
                    val payloadDot = payload.dragActor as Image

                    val isCorrect = (payloadDot.userObject as MatchBox).hijaiyah == frame.hijaiyah

                    // TODO handle correct/incorrect view
                    if (isCorrect) {
                        frame.box.setState(HijaiyahBox.State.CORRECT)
                        (source?.actor as MatchBox).box.setState(HijaiyahBox.State.CORRECT)

                        frame.leftCircle.actor = payloadDot
                        payloadDot.touchable = Touchable.disabled

                        audioService.play(SoundAsset.DROP)
                        audioService.play(SoundAsset.CORRECT_DING)
                    }
                }
            }

        })
    }

    companion object {
        private val log = logger<MatchLineStage>()
    }
}

private data class Line(val sX: Float, val sY: Float, val eX: Float, val eY: Float)

inline fun <S> KWidget<S>.matchLineStage(
    assets: AssetStorage,
    audioService: AudioService,
    batch: Batch,
    gameEventManager: GameEventManager,
    init: MatchLineStage.(S) -> Unit = {}
) = actor(
    MatchLineStage(
        assets,
        audioService,
        batch,
        gameEventManager,
    ), init
)

