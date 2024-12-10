package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
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
    private val stage: Stage,
    private val gameEventManager: GameEventManager,
    skin: Skin = Scene2DSkin.defaultSkin
) : Stack(), KGroup {
    private var drawArea: Image
    private val hijaiyahEntries = Hijaiyah.entries
    lateinit var leftEntries: List<Hijaiyah>
    lateinit var rightEntries: List<Hijaiyah>

    private val leftGroup: VerticalGroup
    private val rightGroup: VerticalGroup
    private val skipButton: ImageTextButton
    private val dragAndDrop = DragAndDrop()
    private lateinit var dragged: Image
    private lateinit var draggedStart: Container<Actor>

    private val drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))

    private val savedLines: MutableList<Line> = mutableListOf()
    private var currentLine: Line? = null

    init {
        table {
            setFillParent(true)

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
                toFront()
                onChangeEvent {
                    this@MatchLineStage.loadStage()
                }
                it.padBottom(PADDING_INNER_SCREEN).align(Align.bottom).expand()
            }

            row()

        }

//        this@MatchLineStage.drawArea = image(Drawables.BOX_ORANGE_ROUNDED.drawable) {
        this@MatchLineStage.drawArea = image(object : ShapeDrawerDrawable(drawer) {
            override fun drawShapes(shapeDrawer: ShapeDrawer?, x: Float, y: Float, width: Float, height: Float) {
                // Draw saved lines
                savedLines.forEach { line ->
                    drawer.line(line.sX, line.sY, line.eX, line.eY, Color.BLACK, 10f)
                }
                // Draw the current line if it exists
                currentLine?.let { line ->
                    drawer.line(line.sX, line.sY, line.eX, line.eY, Color.BLACK, 10f)
                }
                drawer.update()
            }

        })
        {
            setFillParent(true)
//            color = skin.getColor(Colors.GREY.color)
//            it.grow().align(Align.bottomLeft)
            touchable = Touchable.disabled
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
                dragged = draggedDot
//                draggedStart = box.rightCircle
                payload.dragActor = draggedDot
                stage.addActor(draggedDot)
                dragAndDrop.setDragActorPosition(draggedDot.width / 2, -draggedDot.height / 2)
                audioService.play(SoundAsset.STRETCH)

                // Save the starting point of the drag
//                val startX = SCREEN_W/2 - STAGE_BOX_WIDTH/2 + box.x + box.width / 2    // box.rightCircle.x
//                val startY = this@MatchLineStage.y + box.y + box.height / 2  // box.rightCircle.y
//                val startX = box.rightDot.width / 2   // box.rightCircle.x
//                val startY = -box.rightDot.height / 2 // box.rightCircle.y
//
//                currentLine = Line(startX, startY, startX, startY)
                this@MatchLineStage.validate()
                validate()
                (actor as MatchBox).validate()
                val stageCoords = actor.localToScreenCoordinates(Vector2(0f, 0f))
//                val stageCoords = draggedDot.localToScreenCoordinates(vec2(startX, startY))
//                val stageCoords = draggedDot.localToActorCoordinates(this@MatchLineStage, vec2(startX, startY))
//                val stageCoords = draggedDot.localToStageCoordinates(Vector2(startX, startY))
                currentLine = Line(stageCoords.x, stageCoords.y, stageCoords.x, stageCoords.y)
//                currentLine = Line(draggedDot.width / 2, -draggedDot.height / 2,draggedDot.width / 2, -draggedDot.height / 2)

                log.debug { "Init line starting position at (${stageCoords.x},${stageCoords.y})" }
//                log.debug { "Init line starting position at (${startX},${startY})" }

                return payload
            }

            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {

                log.debug { "Dragging line, position: (${currentLine?.sX},${currentLine?.sY}) (${currentLine?.eX},${currentLine?.eY}), in func: (${x},${y}),prop: (${dragged.x},${dragged.y})" }
                // Update the end point of the current line
                currentLine?.let {
//                    it.sX = draggedStart.x +
                    it.eX = dragged.x + dragged.width
                    it.eY = dragged.y + dragged.height
                }
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
                    currentLine = null
                }
            }
        })
    }

    private fun addDragTarget(frame: MatchBox) {

        log.debug { "Adding drag target" }
        dragAndDrop.addTarget(object : DragAndDrop.Target(frame) {
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
                    currentLine = null
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
                        (source?.actor as MatchBox).box.run {
                            setState(HijaiyahBox.State.CORRECT)
                            touchable = Touchable.disabled
                        }

                        frame.leftCircle.actor = payloadDot
                        audioService.play(SoundAsset.DROP)
                        audioService.play(SoundAsset.CORRECT_DING)

                        // Save the line coordinates
                        currentLine?.let {
                            savedLines.add(it)
                            currentLine = null
                        }
                    }
                }
            }

        })
    }

    companion object {
        private val log = logger<MatchLineStage>()
    }
}

private data class Line(var sX: Float, var sY: Float, var eX: Float, var eY: Float)

inline fun <S> KWidget<S>.matchLineStage(
    assets: AssetStorage,
    audioService: AudioService,
    batch: Batch,
    stage: Stage
    gameEventManager: GameEventManager,
    init: MatchLineStage.(S) -> Unit = {}
) = actor(
    MatchLineStage(
        assets,
        audioService,
        batch,
        stage,
        gameEventManager,
    ), init
)
