package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_BETWEEN_BUTTONS
import com.github.doandadr.petualanganhijaiyah.ui.widget.HijaiyahBox
import com.github.doandadr.petualanganhijaiyah.ui.widget.MatchBox
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
    private lateinit var currentEntries: List<Hijaiyah>
    private val leftBoxes = mutableListOf<MatchBox>()
    private val rightBoxes = mutableListOf<MatchBox>()

    private val leftGroup: VerticalGroup
    private val rightGroup: VerticalGroup
    private val skipButton: ImageTextButton

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

    fun handleAnswer(leftIndex: Int, rightIndex: Int) {
        if (leftBoxes[leftIndex].hijaiyah == rightBoxes[rightIndex].hijaiyah) {
            // TODO handle correct
        } else {
            // TODO handle incorrect
        }
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    fun loadStage() {
        currentEntries = pickRandomEntries(6)

        leftBoxes.clear()
        leftGroup.clearChildren()
        currentEntries.shuffled().forEachIndexed { index, hijaiyah ->
            val box = MatchBox(hijaiyah, MatchBox.State.LEFT, assets)
            box.onChangeEvent {
                log.debug { "left box touched" }
            }
            leftBoxes += box
            leftGroup.addActor(leftBoxes[index])
        }

        rightBoxes.clear()
        rightGroup.clearChildren()
        currentEntries.shuffled().forEachIndexed { index, hijaiyah ->
            val box = MatchBox(hijaiyah, MatchBox.State.RIGHT, assets)
            box.box.setState(HijaiyahBox.State.TEXT)
            box.onChangeEvent {
                log.debug { "right box touched" }
            }
            rightBoxes += box
            rightGroup.addActor(rightBoxes[index])
        }
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

