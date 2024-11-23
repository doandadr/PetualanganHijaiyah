package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.ButtonStyles
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelDots
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelButton
import com.github.doandadr.petualanganhijaiyah.ui.widget.stars
import com.github.doandadr.petualanganhijaiyah.util.centerX
import ktx.actors.onChange
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

const val SCALE_FONT_BIG = 1.75f
private val log = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {
    // TODO define variables (such as levels data[stars, score, done]) in here
    // TODO handle in event or data manager

    override fun show() {
        log.debug { "Map Screen is shown" }

        setupUI()
    }

    private fun setupUI() {
        val bgMap = assets[TextureAsset.MAP.descriptor]

//        stage.isDebugAll = true
        // TODO setStates based on data, do in screen

        stage.actors {
            scrollPane {
                setFillParent(true)
                setScrollingDisabled(true, false)
                setSmoothScrolling(false)

                table {
                    background(TextureRegionDrawable(bgMap))
                    align(Align.bottomLeft)

                    floatingGroup {
                        setFillParent(true)

                        levelButton("LEVEL 1", ButtonStyles.LEVEL1.styleName) {
                            setPositions(-30f, -40f, 570f, 380f)
                            setTextRotation(5f)
                            levelDots { setPosition(centerX(width) - 20f, 100f) }
                            stars { setPosition(450f, 200f) }
                            button.onChange {
                                if (isChecked){
                                    game.setScreen<LevelScreen>()
                                }
                            }
                        }

                        levelButton("LEVEL 2", ButtonStyles.LEVEL2.styleName) {
                            setPositions(30f, 440f, 200f, 150f)
                            setTextRotation(-5f)
                            levelDots {
                                setPosition(centerX(width) - 10f, -80f)
                                flipHorizontal()
                            }
                            stars { setPosition(0f, -50f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 3", ButtonStyles.LEVEL3.styleName) {
                            setPositions(450f, 750f, 100f, 230f)
                            setTextRotation(-8f)
                            levelDots { setPosition(-130f, -100f) }
                            stars { setPosition(0f, -50f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 4", ButtonStyles.LEVEL4.styleName) {
                            setPositions(-100f, 1000f, 300f, 150f)
                            setTextRotation(5f)
                            levelDots {
                                setPosition(centerX(width) + 120f, -50f)
                                flipHorizontal()
                            }
                            stars { setPosition(150f, -50f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 5", ButtonStyles.LEVEL5.styleName) {
                            setPositions(-10f, 1370f, 370f, 300f)
                            levelDots { setPosition(centerX(width) - 100f, -100f) }
                            stars { setPosition(250f, 150f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 6", ButtonStyles.LEVEL6.styleName) {
                            setPositions(420f, 1850f, 120f, 200f)
                            setTextRotation(5f)
                            levelDots { setPosition(-120f, -120f) }
                            stars { setPosition(20f, -50f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 7", ButtonStyles.LEVEL7.styleName) {
                            setPositions(30f, 2000f, 200f, 200f)
                            setTextRotation(-10f)
                            levelDots {
                                setPosition(centerX(width) - 30f, -30f)
                                flipHorizontal()
                            }
                            stars { setPosition(20f, 0f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 8", ButtonStyles.LEVEL8.styleName) {
                            setPositions(350f, 2400f, 200f, 100f)
                            setTextRotation(8f)
                            levelDots { setPosition(0f, -180f) }
                            stars { setPosition(100f, -50f) }
                            button.onChange { }
                        }

                        levelButton("LEVEL 9", ButtonStyles.LEVEL9.styleName) {
                            setPositions(50f, 2600f, 100f, 100f)
                            setTextRotation(-5f)
                            levelDots {
                                setPosition(centerX(width) - 40f, -120f)
                                flipHorizontal()
                            }
                            stars { setPosition(0f, -50f) }
                            button.onChange { }
                        }
                    }
                }
                validate()
                scrollPercentY = 1f
            }
            // top sign
            container {
                setFillParent(true)
                top()
                pad(50f)
                label("PETA", "map").setAlignment(Align.center)
            }
        }
    }

    override fun render(delta: Float) {
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
            // TODO do something
        }
    }

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.disposeSafely()
    }
}
