package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.SCREEN_H
import com.github.doandadr.petualanganhijaiyah.SCREEN_W
import com.github.doandadr.petualanganhijaiyah.asset.ButtonStyles
import com.github.doandadr.petualanganhijaiyah.asset.LabelStyles
import com.github.doandadr.petualanganhijaiyah.asset.TextTooltipStyles
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import ktx.actors.onChange
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

const val SCALE_BTN_LARGE = 1.2f
const val SCALE_BTN_SMALL = 0.9f
const val SCALE_FONT_MEDIUM = 1.25f

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
            TextTooltip("This is a book tooltip", skin, TextTooltipStyles.GREEN_YELLOW.styleName)

        // TODO settings window: volume, gender

        val bgHome = assets[TextureAsset.HOME.descriptor]
        stage.actors {

            table {
                setFillParent(true)
                background(TextureRegionDrawable(bgHome))
                align(Align.bottomLeft)

                floatingGroup {
                    setFillParent(true)

                    container {
                        setFillParent(true)
                        align(Align.topLeft)
                        pad(50f)
                        label("PETUALANGAN\nHIJAIYAH", LabelStyles.PRIMARY_GREEN_L.styleName) {
                            setAlignment(Align.center)
                            setOrigin(Align.center)
                            setFontScale(SCALE_FONT_MEDIUM)
                        }
                    }
                    horizontalGroup {
                        setFillParent(true)
                        align(Align.topRight)
                        pad(50f)
                        space(50f)
                        // TODO coin functionalities
                        button(ButtonStyles.COIN.styleName) {
                            isTransform = true
                            setOrigin(Align.right)
                            setScale(SCALE_BTN_LARGE)
                            isVisible = false
                        }
                        button(ButtonStyles.BOOK.styleName) {
                            // TODO practice screen
                            addListener(tooltip)
                        }
                    }
                    verticalGroup {
                        setFillParent(true)
                        center()
                        space(40f)
                        padBottom(250f)
                        textButton("MULAI", "board") {
                            isTransform = true
                            rotation = 10f
                            setOrigin(Align.bottom)
                            setScale(SCALE_BTN_LARGE)
                            onChange {
                                game.setScreen<MapScreen>()
                            }
                        }
                        textButton("PENGATURAN", "board-s") {
                            // TODO onChange open window
                            onChange {
                                if (isChecked) {

                                }
                            }
                        }
                        textButton("KELUAR", "board-s") {
                            isTransform = true
                            setOrigin(Align.center)
                            setScale(SCALE_BTN_SMALL)
                            onChange {
                                if (isChecked) {
                                    Gdx.app.exit()
                                    System.exit(0)
                                }
                            }
                        }
                    }
                    verticalGroup {
                        setFillParent(true)
                        align(Align.bottomLeft)
                        space(20f)
                        container {
                            isTransform = true
                            setOrigin(Align.center)
                            rotation = 5f
                            label("Selamat\nDatang!", "primary-gw")
                        }
                        textButton("AZHARA", "sign") {
                            // TODO onclick open window change name
                        }
                    }


                }
            }
            window("WINDOW") {
                isVisible = false
                setSize(SCREEN_W * 0.8f, SCREEN_H * 0.8f)
                setPosition(SCREEN_W / 2 - width / 2, SCREEN_H / 2 - height / 2)
                align(Align.center)
                textField {

                }
            }

            // TODO TextTooltip sequence on first play; better to just have floating TT pointing to those
            floatingGroup {

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
