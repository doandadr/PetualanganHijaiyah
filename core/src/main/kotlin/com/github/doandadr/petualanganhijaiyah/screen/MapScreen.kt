package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelDotsWidget
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelWidget
import ktx.actors.onChange
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

const val SCALE_FONT_BIG = 1.75f
private val log = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        log.debug { "Map Screen is shown" }

        setupUI()
    }

    private fun setupUI() {
        val bgMap = assets[TextureAsset.MAP.descriptor]

        stage.isDebugAll = true
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


                        levelWidget("LEVEL 1", "primary-gw", "level1") {
                            setPositions(-30f, -40f, 600f, 380f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(5f)
                            buttonLevel.onChange { }
                        }

                        levelWidget("LEVEL 2", "primary-gw", "level2") {
                            setPositions(50f, 400f, 200f, 150f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(-5f)
                            buttonLevel.onChange { }
                        }

                        levelWidget("LEVEL 3", "primary-gw", "level3") {
                            setPositions(450f, 750f, 100f, 230f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(-8f)
                            setButtonScale(1.2f)
                            buttonLevel.onChange { }
                        }

                        levelWidget("LEVEL 4", "primary-gw", "level4") {
                            setPositions(-100f, 1000f, 300f, 150f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(5f)
                            buttonLevel.onChange { }
                        }
                        levelWidget("LEVEL 5", "primary-gw", "level5") {
                            setPositions(-10f, 1370f, 390f, 160f)
                            setTextScale(SCALE_FONT_BIG)
                            buttonLevel.onChange { }
                        }
                        levelWidget("LEVEL 6", "primary-gw", "level6") {
                            setPositions(440f, 1850f, 120f, 100f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(5f)
                            buttonLevel.onChange { }
                        }
                        levelWidget("LEVEL 7", "primary-gw", "level7") {
                            setPositions(30f, 2000f, 200f, 200f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(-10f)
                            buttonLevel.onChange { }
                        }
                        levelWidget("LEVEL 8", "primary-gw", "level8") {
                            setPositions(350f, 2400f, 200f, 70f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(8f)
                            buttonLevel.onChange { }
                        }
                        levelWidget("LEVEL 9", "primary-gw", "level9") {
                            setPositions(50f, 2600f, 100f, 100f)
                            setTextScale(SCALE_FONT_BIG)
                            setTextRotation(-5f)
                            buttonLevel.onChange { }
                        }

                        levelDotsWidget()

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
    }

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.disposeSafely()
    }
}
