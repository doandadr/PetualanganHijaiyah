package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.data.getLevelModelDemo
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_MAP_STAR
import com.github.doandadr.petualanganhijaiyah.ui.widget.LevelButton
import com.github.doandadr.petualanganhijaiyah.ui.widget.StarWidget
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelButton
import com.github.doandadr.petualanganhijaiyah.util.centerX
import ktx.actors.onChange
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

private val log = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {
    // TODO define variables (such as levels data[stars, score, done]) in here
    // TODO handle in event or data manager
    lateinit var testButton: LevelButton

    override fun show() {
        log.debug { "Map Screen is shown" }

        audioService.play(MusicAsset.MAP)

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

                        levelButton("LEVEL 1", Buttons.LEVEL1.style) {
                            setPosition(-30f, -40f)
                            text.setPosition(570f, 380f)
                            rotateText(5f)
                            dots.setPosition(centerX(width) - 20f, 100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(450f, 200f)
                            button.onChange {
                                if (isPressed) {
                                    game.removeScreen<LevelScreen>()
                                    game.addScreen(LevelScreen(game, getLevelModelDemo()))
                                    game.setScreen<LevelScreen>()
                                }
                            }
                        }

                        testButton = levelButton("LEVEL 2", Buttons.LEVEL2.style) {
                            setPosition(30f, 440f)
                            text.setPosition(200f, 150f)
                            rotateText(-5f)
                            dots.setPosition(centerX(width) - 10f, -80f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 3", Buttons.LEVEL3.style) {
                            setPosition(450f, 750f)
                            text.setPosition(100f, 230f)
                            rotateText(-8f)
                            dots.setPosition(-130f, -100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 4", Buttons.LEVEL4.style) {
                            setPosition(-100f, 1000f)
                            text.setPosition(300f, 150f)
                            rotateText(5f)
                            dots.setPosition(centerX(width) + 120f, -50f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(150f, -50f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 5", Buttons.LEVEL5.style) {
                            setPosition(-10f, 1370f)
                            text.setPosition(370f, 300f)
                            dots.setPosition(centerX(width) - 100f, -100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(250f, 150f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 6", Buttons.LEVEL6.style) {
                            setPosition(420f, 1850f)
                            text.setPosition(120f, 200f)
                            rotateText(5f)
                            dots.setPosition(-120f, -120f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(20f, -50f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 7", Buttons.LEVEL7.style) {
                            setPosition(30f, 2000f)
                            text.setPosition(200f, 200f)
                            rotateText(-10f)
                            dots.setPosition(centerX(width) - 30f, -30f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(20f, 0f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 8", Buttons.LEVEL8.style) {
                            setPosition(350f, 2400f)
                            text.setPosition(200f, 100f)
                            rotateText(8f)
                            dots.setPosition(0f, -180f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(100f, -50f)
                            button.onChange { }
                        }

                        levelButton("LEVEL 9", Buttons.LEVEL9.style) {
                            setPosition(50f, 2600f)
                            text.setPosition(100f, 100f)
                            rotateText(-5f)
                            dots.setPosition(centerX(width) - 40f, -120f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                            button.onChange { }
                        }
                    }
                }
                validate()
                scrollPercentY = 1f
            }
            // top ui
            horizontalGroup {
                setFillParent(true)
                top()
                pad(50f)
                label("PETA", "map").setAlignment(Align.center)
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
            testButton.setState(LevelButton.LevelButtonState.INACCESSIBLE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            testButton.setState(LevelButton.LevelButtonState.AVAILABLE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            testButton.setState(LevelButton.LevelButtonState.PASSED)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            testButton.starWidget.setState(StarWidget.StarState.HIDDEN)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            testButton.starWidget.setState(StarWidget.StarState.ZERO)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            testButton.starWidget.setState(StarWidget.StarState.ONE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            testButton.starWidget.setState(StarWidget.StarState.TWO)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            testButton.starWidget.setState(StarWidget.StarState.THREE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            testButton.starWidget.setState(StarWidget.StarState.HIDDEN)
            testButton.setState(LevelButton.LevelButtonState.HIDDEN)
        }
    }

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.disposeSafely()
    }
}
