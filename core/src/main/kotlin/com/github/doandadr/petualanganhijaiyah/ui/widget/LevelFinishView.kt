package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.ImageButtons
import com.github.doandadr.petualanganhijaiyah.asset.ImageTextButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.ui.values.BANNER_TOP_PADDING
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import ktx.actors.onChangeEvent
import ktx.scene2d.*

class LevelFinishView(
    // levelData: LevelModel,
    // star, score, time, levelName
    skin: Skin = Scene2DSkin.defaultSkin
): Table(skin), KTable {
    // When level is finished through an event, we get the stage, add levelfinish view to it, construct and animate all of the elements, we do not keep track of it, if we move on to a different screen,

    private val nextButton: ImageButton
    private val repeatButton: ImageButton
    private val menuButton: ImageButton
    private val scoreView: ImageTextButton
    private val stars: StarWidget
    private val starStack: Stack

    private var currentScore: Float = 0f
    private var currentStars: Int = 0

    private val starState: StarWidget.StarState = StarWidget.StarState.THREE // TODO get from levelData
    private val scoreValue: Float = 1000f // TODO get from levelData

    init {
        label("BERHASIL", Labels.BANNER_ORANGE.style) {
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
                this@LevelFinishView.stars = starWidget {
                    setFillParent(true) // TODO check if stars is centered
                }
                it.expandY().align(Align.center)
            }

            row()
            this@LevelFinishView.scoreView = imageTextButton("1000", ImageTextButtons.LEVEL_FINISH_SCORE.style) {
                it.spaceBottom(30f)
            }

            row()
            horizontalGroup {
                space(30f)
                this@LevelFinishView.menuButton = imageButton(ImageButtons.MENU.style)
                this@LevelFinishView.repeatButton = imageButton(ImageButtons.REPEAT.style)
                this@LevelFinishView.nextButton = imageButton(ImageButtons.NEXT.style)
                it.padBottom(15.0f).spaceBottom(16.0f)
            }

            it.prefSize(LEVEL_COMPLETE_POPUP_SIZE)
        }

        setupHandlers()
    }

    fun update(score: Float, stars: Int) {
        // score and stars, and maybe highscore

    }

    private fun setupHandlers() {
        // set star state
        stars.setState(starState)
        // set score
        scoreView.label.setText(scoreValue.toInt().toString())

        // set button listeners
        menuButton.onChangeEvent {
            // go back to MapScreen, TODO in LevelScreen
        }
        repeatButton.onChangeEvent {
            // reload level screen // TODO in LevelScreen
            // as if going back to map screen then entering level again
        }
        nextButton.onChangeEvent {
            // go to next level (level + 1) // TODO in LevelScreen
        }
    }

    companion object {
        private const val LEVEL_COMPLETE_POPUP_SIZE = 460f
    }
}

inline fun <S> KWidget<S>.levelFinishView(
    skin: Skin = Scene2DSkin.defaultSkin,
    init: LevelFinishView.(S) -> Unit = {}
) = actor(
    LevelFinishView(
        skin
    ), init
)
