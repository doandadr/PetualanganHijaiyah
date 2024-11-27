package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.*
import com.kotcrab.vis.ui.layout.FloatingGroup
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup
import ktx.actors.*


private val log = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {
    private lateinit var nameChange: Table
    private lateinit var homeLayout: Table
    private lateinit var homeUI: FloatingGroup
    private lateinit var settingsPopup: SettingsPopup
    private lateinit var characterSelectPopup: CharacterSelectPopup

    override fun show() {
        log.debug { "Home Screen is shown" }
        setupUI()

        audioService.enabled = true
        // play music
         audioService.play(MusicAsset.HOME, 0.5f)
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        // TODO tooltips
        val tooltip: TextTooltip =
            TextTooltip("This is a book tooltip", skin, TextTooltips.GREEN_YELLOW.style)

        // TODO settings window: volume, gender
        val bgHome = assets[TextureAsset.HOME.descriptor]
        val bgHomeDim = assets[TextureAsset.HOME_DIM.descriptor]

        // Setup actions and animations

        stage.actors {

            homeLayout = table {
                background(TextureRegionDrawable(bgHome))
                setFillParent(true)
                align(Align.bottomLeft)

                homeUI = floatingGroup {
                    setFillParent(true)

                    container {
                        setFillParent(true)
                        align(Align.topLeft)
                        pad(50f)
                        label("PETUALANGAN\nHIJAIYAH", Labels.PRIMARY_GREEN_L.style) {
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
                        button(Buttons.COIN.style) {
                            isTransform = true
                            setOrigin(Align.right)
                            setScale(SCALE_BTN_MEDIUM)
                            isVisible = false
                        }
                        button(Buttons.BOOK.style) {
                            // TODO practice screen
                            addListener(tooltip)
                            onChange {
                                game.setScreen<PracticeScreen>()
                                audioService.play(SoundAsset.CLICK_BUTTON)
                            }
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
                            setScale(SCALE_BTN_MEDIUM)

                            onChange {
                                game.setScreen<MapScreen>()
                            }
                        }
                        textButton("PENGATURAN", "board-s") {
                            onChange {
                                homeUI.isVisible = false
                                homeLayout.background = TextureRegionDrawable(bgHomeDim)
                                settingsPopup.isVisible = true
                            }
                        }
                        textButton("KELUAR", "board-s") {
                            isTransform = true
                            setOrigin(Align.center)
                            setScale(SCALE_BTN_SMALL)
                            onChange {
                                if (isChecked) {
                                    // TODO dispatch save game event
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
                            rotation = 10f
                            label("Selamat\nDatang!", "primary-gw")
                        }
                        textButton("AZHARA", "sign") {
                            // TODO onclick open window change name
                        }
                    }
                }
            }

            // TODO settings data interaction
            settingsPopup = settingsPopup {
                volumeSlider.onChange {
                    // TODO onchange volume slider
                }
                confirmButton.onChange {

                    // TODO popup on and off system
                    homeUI.isVisible = true
                    settingsPopup.isVisible = false
                    homeLayout.background = TextureRegionDrawable(bgHome)
                }
            }

            characterSelectPopup = characterSelectPopup {
                girlButton.onChange {
                    // TODO CHANGE GENDER ON PREFERENCE
                }
                boyButton.onChange {
                    // TODO CHANGE GENDER ON PREFERENCE
                }
            }

            nameChange  = nameChangePopup {
                confirmButton.onChange {

                }
                nameField.onChange {

                }
            }

            // TODO TextTooltip sequence on first play; better to just have floating TT pointing to those

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
