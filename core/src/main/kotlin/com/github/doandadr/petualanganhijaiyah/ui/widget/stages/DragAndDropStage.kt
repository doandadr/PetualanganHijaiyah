package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import ktx.actors.onChangeEvent
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*

class DragAndDropStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    skin: Skin = Scene2DSkin.defaultSkin,
):Table(skin), KTable {
    private val hijaiyahEntries = Hijaiyah.entries
    lateinit var dragEntries: List<Hijaiyah>
    lateinit var dropEntries: List<Hijaiyah>

    private val dragGroup: HorizontalGroup
    private val dropGroup: HorizontalGroup
    private val skipButton: ImageTextButton
    private val dragAndDrop = DragAndDrop()

    init {
        setBackground(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))

        this@DragAndDropStage.dropGroup = horizontalGroup {
            space(50f)
            it.padTop(PADDING_INNER_SCREEN).padLeft(PADDING_INNER_SCREEN).padRight(PADDING_INNER_SCREEN).expand()
        }

        row()
        this@DragAndDropStage.dragGroup = horizontalGroup {
            space(50f)
            it.spaceTop(200.0f).expand()
        }

        row()
        this@DragAndDropStage.skipButton = imageTextButton("   Lewati", ImageTextButtons.SKIP.style) {
            onChangeEvent {
                this@DragAndDropStage.loadStage()
            }
            it.spaceTop(50f).padBottom(PADDING_INNER_SCREEN)
        }

        loadStage()
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {
        dragGroup.clearChildren()
        dragEntries = pickRandomEntries(3)
        dragEntries.shuffled().forEachIndexed { index, letter ->
            val box = HijaiyahBox(letter, HijaiyahBox.Size.MEDIUM, assets)
            val frame = Container<HijaiyahBox>().size(SIZE_HIJAIYAH_MEDIUM)
            box.userObject = frame
            frame.actor = box

            setupDragBox(box)
            setupDragFrame(frame)

            dragGroup.addActor(frame)
        }

        dropGroup.clearChildren()
        dropEntries = dragEntries.shuffled()
        dropEntries.forEachIndexed { index, letter ->
            val box = HijaiyahBox(letter, HijaiyahBox.Size.MEDIUM, assets)
            val frame = Container<HijaiyahBox>().size(SIZE_HIJAIYAH_MEDIUM)
            box.setState(HijaiyahBox.State.DROP)
            box.userObject = frame
            frame.setBackground(skin.getDrawable(Drawables.ICONBUTTON_BACKGROUND_ROUNDED.drawable))
            frame.actor = box

            setupDropFrame(frame)

            dropGroup.addActor(frame)
        }

    }

    private fun setupDragBox(box: HijaiyahBox) {
        dragAndDrop.addSource(object :DragAndDrop.Source(box) {
            override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                val payload : DragAndDrop.Payload = DragAndDrop.Payload()
                payload.dragActor = actor
                stage.addActor(actor)
                dragAndDrop.setDragActorPosition(actor.width/2, -actor.height/2)

                // TODO play sound
                audioService.play(SoundAsset.POP_LOW)

                return payload
            }

            override fun dragStop(
                event: InputEvent?,
                x: Float,
                y: Float,
                pointer: Int,
                payload: DragAndDrop.Payload?,
                target: DragAndDrop.Target?
            ) {
                if (target == null ) {
                    (box.userObject as Container<HijaiyahBox>).actor = box

                    audioService.play(SoundAsset.CANCEL)
                } else if ((target.actor as Container<HijaiyahBox>).actor.hijaiyah != box.hijaiyah) {
                    (box.userObject as Container<HijaiyahBox>).actor = box
                    audioService.play(SoundAsset.FAILURE)

                    // TODO handle incorrect
                }
            }
        })
    }

    private fun setupDragFrame(frame: Container<HijaiyahBox>) {
        dragAndDrop.addTarget(object : DragAndDrop.Target(frame) {
            override fun drag(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ): Boolean {
                return true
            }

            override fun drop(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ) {
                if (payload != null) {
                    val payloadBox = payload.dragActor as HijaiyahBox
                    val frameBox = frame.actor

                    if (frameBox == null) {
                        frame.actor = payloadBox
                        audioService.play(SoundAsset.DROP)
                    } else if(frameBox.hijaiyah == payloadBox.hijaiyah) {
                        frame.actor = payloadBox
                    }
                }
            }
        })
    }

    private fun setupDropFrame(frame: Container<HijaiyahBox>) {
        dragAndDrop.addTarget(object : DragAndDrop.Target(frame) {
            override fun drag(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ): Boolean {
                return true
            }

            override fun drop(
                source: DragAndDrop.Source?,
                payload: DragAndDrop.Payload?,
                x: Float,
                y: Float,
                pointer: Int
            ) {
                if (payload != null) {
                    val payloadBox = payload.dragActor as HijaiyahBox
                    val frameBox = frame.actor

                    // If incorrect / not match, ignore, handle incorrect, play sound
                    val isCorrect = payloadBox.hijaiyah == frameBox.hijaiyah

                    // If correct, handle correct, change style of box to green
                    if (isCorrect) {
                        payloadBox.setState(HijaiyahBox.State.CORRECT)
                        frame.actor = payloadBox
                        payload.dragActor.touchable = Touchable.disabled
                        audioService.play(SoundAsset.DROP)
                        // TODO update count number of correct
                    } else {
                        // TODO dispatch event
                    }
                }
            }
        })
    }

    fun handleAnswer(index: Int) {

    }

    companion object {
        private val log = logger<DragAndDropStage>()
    }
}

inline fun <S> KWidget<S>.dragAndDropStage(
    assets: AssetStorage,
    audioService: AudioService,
    init: DragAndDropStage.(S) -> Unit = {}
) = actor(
    DragAndDropStage(
        assets,
        audioService
    ), init
)
