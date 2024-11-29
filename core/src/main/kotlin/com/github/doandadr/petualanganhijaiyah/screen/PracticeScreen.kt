package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
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
        val bgPractice = assets[TextureAsset.STAGE.descriptor]

        audioService.play(MusicAsset.FIELD)

        stage.actors {
            table {
                background(TextureRegionDrawable(bgPractice))
                setFillParent(true)

                button(Buttons.BACK.style) {
                    onChange {
                        game.setScreen<HomeScreen>()
                    }
                    it.align(Align.topLeft)
                }
                row()
                container {
                    label("Ayo Belajar!", Labels.PRIMARY_GREEN_L.style)
                    isTransform = true
                    setOrigin(Align.center)

                    it.padBottom(0f)
                    // TODO test
                }
                row()

                practiceStage(assets, audioService)
            }
        }
    }

    override fun render(delta: Float) {
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
