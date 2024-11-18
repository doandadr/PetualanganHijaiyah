package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.actors.onClick
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import javax.swing.text.StyleConstants.setAlignment

const val LARGE_BTN_SCALE = 1.2f
const val SMALL_BTN_SCALE = 0.9f
private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {
    override fun resize(width: Int, height: Int) {
        super.resize(width, height);
        stage.viewport.update(width, height, true);
    }

    override fun show() {
        LOG.debug { "Home Screen is shown" }

        audioService.play(MusicAsset.HOME)
        setupUI()
    }

    private fun setupUI() {
        val bgHome = assets[TextureAsset.HOME.descriptor]
        stage.isDebugAll = true
        stage.actors {
            image(bgHome)

            container {
                setFillParent(true)
                align(Align.topLeft)
                pad(50f)
                label("PETUALANGAN\nHIJAIYAH", "primary-gw").setAlignment(Align.center)
            }
            horizontalGroup {
                setFillParent(true)
                align(Align.topRight)
                pad(50f)
                space(50f)
                button("coin") {
                    isTransform = true
                    setOrigin(Align.right)
                    setScale(LARGE_BTN_SCALE)
                }
                button("book") {
                    // TODO practice screen
                }
            }
            verticalGroup {
                setFillParent(true)
                center()
                space(30f)
                padBottom(250f)
                textButton("MULAI", "board") {
                    isTransform = true
                    rotation = 10f
                    setOrigin(Align.bottom)
                    setScale(LARGE_BTN_SCALE)
                    onClick {
                        game.setScreen<MapScreen>()
                    }
                }
                textButton("PENGATURAN", "board-s") {
                    // TODO onclick open window



                }

                textButton("KELUAR", "board-s") {
                    isTransform = true
                    setOrigin(Align.center)
                    setScale(SMALL_BTN_SCALE)
                    onClick {
                        // TODO confirm window
                        // TODO save game
                        Gdx.app.exit()
                        System.exit(0)
                    }
                }
            }
            verticalGroup {
                setFillParent(true)
                left()
                bottom()
                container {
                    top()
                    setOrigin(Align.center)
                    rotation = 1f
                    label("Selamat\nDatang!", "primary-gw") {

                    }
                }
                textButton("AZHARA", "sign") {
                    // TODO onclick open window change name
                }
            }
        }
    }

    override fun render(delta: Float) {
        stage.run {
            // debug keys


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
