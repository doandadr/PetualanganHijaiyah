package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.actors.onClick
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*

const val SCALE_BTN_LARGE = 1.2f
const val SCALE_BTN_SMALL = 0.9f
private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {
    override fun resize(width: Int, height: Int) {
        super.resize(width, height);
        stage.viewport.update(width, height, true);
    }

    override fun show() {
        LOG.debug { "Home Screen is shown" }
        setupUI()

        // play music
//         audioService.play(MusicAsset.HOME, volume = 0.5f)
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        // TODO tooltips
        val tooltip: TextTooltip =
            TextTooltip("This is a book tooltip",skin, "tooltip-gy")

        // TODO settings window: volume, gender


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
                // TODO coin functionalities
                button("coin") {
                    isTransform = true
                    setOrigin(Align.right)
                    setScale(SCALE_BTN_LARGE)
                    isVisible = false
                }
                button("book") {
                    // TODO practice screen
                    addListener(tooltip)
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
                    setScale(SCALE_BTN_LARGE)
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
                    setScale(SCALE_BTN_SMALL)
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
