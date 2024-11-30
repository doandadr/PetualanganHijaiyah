package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.ImageButtons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.STAGE_BOX_HEIGHT
import com.github.doandadr.petualanganhijaiyah.ui.values.STAGE_BOX_WIDTH
import com.github.doandadr.petualanganhijaiyah.ui.widget.stages.practiceStage
import ktx.actors.onChange
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<PracticeScreen>()

// TODO fix layout
class PracticeScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        log.debug { "Practice Screen is shown" }
        setupUI()
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        val bgPractice = assets[TextureAsset.STAGE.descriptor]

        audioService.play(MusicAsset.FIELD)

        stage.actors {
            table {
                background(TextureRegionDrawable(bgPractice))
                setFillParent(true)

                imageButton(ImageButtons.BACK.style) {
                    onChange {
                        game.setScreen<HomeScreen>()
                    }
                    it.expand().align(Align.topLeft).padTop(PADDING_INNER_SCREEN).padLeft(PADDING_INNER_SCREEN)
                }
                row()

                label("Ayo Belajar!", Labels.TEXTBOX_WHITE_SQUARE_LARGE.style) {
                    setAlignment(Align.center)
                    setFontScale(SCALE_BTN_MEDIUM)
                }
                row()

                practiceStage(assets, audioService) {
                    it.prefSize(STAGE_BOX_WIDTH, STAGE_BOX_HEIGHT-200).spaceTop(100f)
                }
                row()

                imageButton(ImageButtons.QUESTION.style) {
                    it.expand().align(Align.bottomRight).padBottom(PADDING_INNER_SCREEN).padRight(PADDING_INNER_SCREEN)
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

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.disposeSafely()
    }
}
