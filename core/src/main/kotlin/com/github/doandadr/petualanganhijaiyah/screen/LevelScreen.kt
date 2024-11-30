package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.ui.values.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.stages.mcqStage
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<LevelScreen>()

class LevelScreen(game: Main) : BaseScreen(game) {
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
                    space(SPACE_BUTTON)
                    // TODO timer bar
                    table {
                        setBackground(skin.getDrawable("timer"))
                    }
                    // TODO timer counter
                    table {
                        setBackground(skin.getDrawable("timer counter"))
                    }
                    it.padLeft(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topLeft)
                }

                label(userName, Labels.SIGN_NAME.style) {
                    setAlignment(Align.center)
                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topRight)
                }
                row()

                table { it ->
                    label(levelName, Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                        setAlignment(Align.center)
                        setFontScale(SCALE_FONT_MEDIUM)
                    }

                    row()
                    image(Drawables.BISMILLAH.drawable)

                    row()
                    // TODO stage box
                    mcqStage(assets, audioService) {
                        it.prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT_MCQ)
                    }
                    it.colspan(2)
                }

                row()
                horizontalGroup {
                    space(SPACE_BUTTON)
                    imageButton(ImageButtons.BACK.style)
                    imageButton(ImageButtons.HOME.style)
                    it.padLeft(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).expand().align(Align.bottomLeft)
                }
                horizontalGroup {
                    space(SPACE_BUTTON)
                    imageButton(ImageButtons.QUESTION.style)
                    imageButton(ImageButtons.HINT.style)
                    it.padRight(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).align(Align.bottomRight);
                }
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
    }


    override fun dispose() {
        stage.disposeSafely()
    }
}
