package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.IntAction
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.ui.values.BANNER_TOP_PADDING
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import ktx.scene2d.*

class LevelFinishView(
    private val score: Float,
    private val star: Int,
    private val time: Float,
    private val state: State,
    private val audioService: AudioService,
    private val isBestScore: Boolean,
    private val isBestTime: Boolean,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {

    private var bestScoreImg: Image
    private var bestTimeImg: Image
    private var timeView: Label
    val nextButton: ImageButton
    val repeatButton: ImageButton
    val menuButton: ImageButton
    private val scoreView: Label
    private val stars: StarWidget
    private val starStack: Stack

    private val starState: StarWidget.StarState = run {
        when (star) {
            1 -> StarWidget.StarState.ONE
            2 -> StarWidget.StarState.TWO
            3 -> StarWidget.StarState.THREE
            else -> StarWidget.StarState.ZERO
        }
    }

    init {
        label(if (state == State.FINISH) "BERHASIL" else "GAGAL", Labels.BANNER_ORANGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_FONT_MEDIUM)
            it.padBottom(BANNER_TOP_PADDING)
        }

        row()
        table {
            toBack()
            setBackground(skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable))

            this@LevelFinishView.starStack = stack {
                image(Drawables.LEVEL_FINISH_STARBOX.drawable) {
                    setScaling(Scaling.none)
                }
                this@LevelFinishView.stars = starWidget(this@LevelFinishView.audioService) {
                    setFillParent(true) // TODO check if stars is centered
                    width = parent.width
                }
                it.padTop(80.0f).padBottom(30.0f).expandY().colspan(3).align(Align.center)
            }

            row()
            image(Drawables.ICON_DIAMOND.drawable) {
                toFront()
                setScaling(Scaling.none)
                it.padRight(-40.0f).spaceBottom(30.0f).align(Align.right)
            }
            this@LevelFinishView.scoreView = label("", Labels.TEXTBOX_BLUE_ROUNDED.style) {
                toBack()
                setAlignment(Align.center)
                it.spaceBottom(30.0f).fillX()
            }
            this@LevelFinishView.bestScoreImg = image(Drawables.ICON_PRIZE.drawable) {
                setScaling(Scaling.none)
                it.padLeft(-40.0f).spaceBottom(30.0f).align(Align.topLeft)
            }

            row()
            image(Drawables.ICON_CLOCK.drawable) {
                toFront()
                setScaling(Scaling.none)
                it.padRight(-40.0f).align(Align.right)
            }
            this@LevelFinishView.timeView = label("", Labels.TEXTBOX_ORANGE_ROUNDED.style) {
                toBack()
                setAlignment(Align.center)
                it.fillX()
            }
            this@LevelFinishView.bestTimeImg = image(Drawables.ICON_PRIZE.drawable) {
                setScaling(Scaling.none)
                it.padLeft(-40.0f).align(Align.left)
            }

            row()
            horizontalGroup {
                space(30f)
                this@LevelFinishView.menuButton = imageButton(ImageButtons.MENU.style)
                this@LevelFinishView.repeatButton = imageButton(ImageButtons.REPEAT.style)
                this@LevelFinishView.nextButton = imageButton(ImageButtons.NEXT.style)
                it.padBottom(15.0f).spaceBottom(16.0f).expandY().align(Align.bottom).colspan(3)
            }

            it.prefWidth(POPUP_WIDTH).prefHeight(POPUP_HEIGHT)
        }

        loadWidget()
    }

    private fun loadWidget() {
        stars.setAnimated(true)
        stars.setState(starState)
        scoreView.setText(score.toInt().toString())
        timeView.setText(formatMMSS(time))

        scoreView.clearActions()
        scoreView.addAction(IntAction(0, score.toInt(), 2f, Interpolation.fade))

        timeView.clearActions()
        timeView.addAction(IntAction(0, time.toInt(), 2f, Interpolation.fade))

        bestScoreImg.isVisible = isBestScore
        bestTimeImg.isVisible = isBestTime
    }

    private fun formatMMSS(time: Float): String {
        val min = (time / 60).toInt().toString().padStart(2, '0')
        val sec = (time % 60).toInt().toString().padStart(2, '0')
        return "$min:$sec"
    }

    enum class State {
        FINISH,
        FAILED,
    }

    companion object {
        private const val POPUP_WIDTH = 450f
        private const val POPUP_HEIGHT = 600f
    }
}

inline fun <S> KWidget<S>.levelFinishView(
    score: Float,
    star: Int,
    time: Float,
    state: LevelFinishView.State,
    audioService: AudioService,
    bestScore: Boolean = false,
    bestTime: Boolean = false,
    init: LevelFinishView.(S) -> Unit = {}
) = actor(
    LevelFinishView(
        score,
        star,
        time,
        state,
        audioService,
        bestScore,
        bestTime,
    ), init
)
