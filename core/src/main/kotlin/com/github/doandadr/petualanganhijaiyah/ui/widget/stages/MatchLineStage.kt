package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_BETWEEN_BUTTONS
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import com.github.doandadr.petualanganhijaiyah.ui.widget.MatchBox
import ktx.actors.alpha
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class MatchLineStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private val hijaiyahEntries = Hijaiyah.entries
    lateinit var leftEntries: List<Hijaiyah>
    lateinit var rightEntries: List<Hijaiyah>

    private val leftGroup: VerticalGroup
    private val rightGroup: VerticalGroup
    private val skipButton: ImageTextButton
    private val dragAndDrop = DragAndDrop()

    //    private var pixmap = Pixmap(skin.getDrawable(Drawables.CIRCLE_BRUSH.drawable))
    private val savedLines: MutableList<Line> = mutableListOf()
    private lateinit var shapeRenderer: ShapeRenderer

    init {
        background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
        horizontalGroup {
            space(150f)
            this@MatchLineStage.leftGroup = verticalGroup {
                space(SPACE_BETWEEN_BUTTONS)
            }

            this@MatchLineStage.rightGroup = verticalGroup {
                space(SPACE_BETWEEN_BUTTONS)
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

            addDragSource(box.rightDot)
            addDragTarget(box)
            leftGroup.addActor(box)
        }

        rightGroup.clearChildren()
        rightEntries = leftEntries.shuffled()
        rightEntries.shuffled().forEachIndexed { index, hijaiyah ->
            val box = MatchBox(hijaiyah, MatchBox.State.RIGHT, assets)
            box.box.setState(HijaiyahBox.State.TEXT)
            box.onChangeEvent {
                log.debug { "right box touched" }
            }
            box.leftDot.userObject = box

            addDropTarget(box)
            rightGroup.addActor(box)
        }
    }

    private fun addDragSource(dot: Image) {
        log.debug { "Adding drag source" }
        dragAndDrop.addSource(object : DragAndDrop.Source(dot) {
            override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                log.debug { "Start dragging source" }
                val payload: DragAndDrop.Payload = DragAndDrop.Payload()
                actor.alpha = 1f
                payload.dragActor = actor
                stage.addActor(actor)
                dragAndDrop.setDragActorPosition(actor.width / 2, -actor.height / 2)

//                val dragCircle = (actor.userObject as MatchBox).leftCircle
//                val circleStageCoords = dragCircle.localToStageCoordinates(Vector2(0f, 0f))
//                savedLines.add(Line(circleStageCoords.x, circleStageCoords.y, actor.x, actor.y))

                audioService.play(SoundAsset.STRETCH)

                return payload
            }

            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                log.debug { "Dragging source" }
                super.drag(event, x, y, pointer)
                // TODO draw a line from actor.userObject.leftCircle to current location
//                shapeRenderer.begin()
//                shapeRenderer.rectLine()
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

                val sourceBox = dot.userObject as MatchBox

                if (target == null) {
                    sourceBox.rightCircle.actor = dot
                    audioService.play(SoundAsset.CANCEL)
                } else if ((target.actor as MatchBox).hijaiyah != sourceBox.hijaiyah) {
                    sourceBox.rightCircle.actor = dot
                    audioService.play(SoundAsset.FAILURE)
                    // TODO handle incorrect
                }
            }
        })
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
                        // TODO set state of source and target box to correct
                        frame.box.setState(HijaiyahBox.State.CORRECT)
//                        (source?.actor as MatchBox).box.setState(HijaiyahBox.State.CORRECT)
                        log.debug { "Source is $source" }

                    }
                }
            }

        })
    }


    companion object {
        private val log = logger<MatchLineStage>()
    }
}

inline fun <S> KWidget<S>.matchLineStage(
    assets: AssetStorage,
    audioService: AudioService,
    init: MatchLineStage.(S) -> Unit = {}
) = actor(
    MatchLineStage(
        assets,
        audioService,
    ), init
)

private data class Line(val startX: Float, val startY: Float, val endX: Float, val endY: Float)

