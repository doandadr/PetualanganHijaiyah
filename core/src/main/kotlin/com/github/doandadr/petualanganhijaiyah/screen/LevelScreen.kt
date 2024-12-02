package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SPACE_BETWEEN_BUTTONS
import com.github.doandadr.petualanganhijaiyah.ui.widget.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.stages.dragAndDropStage
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<LevelScreen>()

class LevelScreen(game: Main) : BaseScreen(game) {
    // levelModel
    // levelFinishView
    private lateinit var finishView: LevelFinishView

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun show() {
        log.debug { "Stage Screen is shown" }
        setupUI()

        audioService.play(MusicAsset.FIELD)
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        val bgStage = assets[TextureAsset.FOREST_1.descriptor]
        val bgStage2 = assets[TextureAsset.FOREST_2.descriptor]
        var userName = "AZHARA" // TODO take from preferences
        var levelName = "LEVEL 1" // TODO take from input from mapscreen

//        stage.isDebugAll = true
        stage.actors {
            table {
                setFillParent(true)
                background(TextureRegionDrawable(bgStage))


                verticalGroup {
                    columnAlign(Align.left)
                    space(SPACE_BETWEEN_BUTTONS)
                    // TODO timer bar
                    timerBar()
                    // TODO timer counter
                    timerCounter()
                    it.padLeft(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topLeft)
                }

//                label(userName, Labels.SIGN_NAME.style) {
//                    setAlignment(Align.center)
//                    setFontScale(5f/userName.length)
//                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topRight)
//                }

                playerInfo(userName, 5, "male") {
                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topRight)
                }

                row()
                table {
                    label(levelName, Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                        setAlignment(Align.center)
                        setFontScale(SCALE_FONT_MEDIUM)
                    }

                    row()
                    image(Drawables.BISMILLAH.drawable)

                    row()
                    // TODO stage box
//                    mcqStage(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
//                    }
//                    mcqVoiceStage(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
//                    }
//                    mcqJoin(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
//                    }
                    dragAndDropStage(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
//                    }
//                    matchLineStage(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
//                    }
//                    drawingStage(assets, audioService) {
//                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
                    }
                    it.colspan(2).expand()
                }

                row()
                horizontalGroup {
                    space(SPACE_BETWEEN_BUTTONS)
                    imageButton(ImageButtons.HOME.style)
                    imageButton(ImageButtons.BACK.style)
                    it.padLeft(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).expand().align(Align.bottomLeft)
                }
                horizontalGroup {
                    space(SPACE_BETWEEN_BUTTONS)
                    imageButton(ImageButtons.QUESTION.style)
                    imageButton(ImageButtons.HINT.style)
                    it.padRight(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).align(Align.bottomRight);
                }
            }

            finishView = levelFinishView {
                setFillParent(true)
                // TODO visibility based on levelComplete
                isVisible = false
            }
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.run {
            viewport.apply()
            act()
            draw()
        }

        debugMode()
    }

    private fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {

        }
    }


    override fun dispose() {
        stage.disposeSafely()
    }
}
