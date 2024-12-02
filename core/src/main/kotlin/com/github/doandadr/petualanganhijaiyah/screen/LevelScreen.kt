package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.data.LevelModel
import com.github.doandadr.petualanganhijaiyah.data.StageModel
import com.github.doandadr.petualanganhijaiyah.data.StageType
import com.github.doandadr.petualanganhijaiyah.event.GameEventListener
import com.github.doandadr.petualanganhijaiyah.ui.values.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.stages.*
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<LevelScreen>()

class LevelScreen(
    game: Main,
    private val model: LevelModel
) : BaseScreen(game), GameEventListener {
    private val timerSecondsData: Float = model.timerSeconds
    private val maxHeart: Int = model.maxHealth
    var userName = "AZHARA" // TODO take from preferences
    var levelName = model.name

    private lateinit var stageBoard: Table
    private lateinit var timer : TimerWidget
    private lateinit var playerInfo: PlayerInfoWidget
    private lateinit var finishView: LevelFinishView

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun show() {
        log.debug { "Stage Screen is shown" }


        audioService.play(MusicAsset.FIELD)

        setupUI()
        setupStage(model.stages.first())
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        val bgStage = assets[TextureAsset.FOREST_1.descriptor]
        val bgStage2 = assets[TextureAsset.FOREST_2.descriptor]

        //        stage.isDebugAll = true
        stage.actors {
            table {
                setFillParent(true)
                background(TextureRegionDrawable(bgStage))

                timer = timerWidget(
                    timerSecondsData,
                    gameEventManager
                ) {
                    it.padLeft(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topLeft)
                }

                playerInfo = playerInfoWidget(userName, maxHeart, "male") {
                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).align(Align.topRight)
                }

                row()
                table {
                    label(levelName, Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                        setAlignment(Align.center)
                        setFontScale(SCALE_FONT_MEDIUM)
                    }

                    row()
                    image(Drawables.BISMILLAH.drawable)

                    it.colspan(2)
                }

                row()
                stageBoard = table {
                    // TODO stage box
                    it.colspan(2).expand().prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT)
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
        timer.update(delta)

        debugMode()
    }

    private fun setupStage(stageModel: StageModel) {
        // TODO add roundCount and entryCount
        val newStage = scene2d {
            when(stageModel.type) {
                StageType.MULTIPLE_CHOICE_LETTER -> mcqStage(assets, audioService)
                StageType.MULTIPLE_CHOICE_JOIN -> mcqJoin(assets, audioService)
                StageType.MULTIPLE_CHOICE_VOICE -> mcqVoiceStage(assets, audioService)
                StageType.DRAG_AND_DROP -> dragAndDropStage(assets, audioService)
                StageType.MATCH_LINE -> matchLineStage(assets, audioService)
                StageType.DRAWING -> drawingStage(assets, audioService)
            }
        }
        newStage.setFillParent(true)
        stageBoard.clear() // TODO check if clearChildren is the way
        stageBoard.addActor(newStage)
    }

    private fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            // refresh
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // finish level
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // restart level
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // go to the next level
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            // level hint
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            // redo tutorial for this level
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {

        }
    }

    // TODO reload level, with levelModel as argument
    private fun reloadLevel(level: LevelModel) {

    }




    override fun dispose() {
        stage.disposeSafely()
    }
}
