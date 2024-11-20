package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.AtlasRegion
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

const val SCALE_FONT_BIG = 1.75f
private val LOG = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        LOG.debug { "Map Screen is shown" }

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
                    debug()
                    background(TextureRegionDrawable(bgMap))
                    align(Align.bottomLeft)

                    floatingGroup {
                        setFillParent(true)

                        floatingGroup {
                            setPosition(0f, -30f)
                            image(assets[TextureAtlasAsset.DRAWABLE.descriptor].findRegion(AtlasRegion.L01_ROCK_BOULDER.region))
                            label("LEVEL 1", "primary-gw") {
                                setPosition(450f, 350f)
                                setFontScale(SCALE_FONT_BIG)
                            }

                        }
                        floatingGroup {
                            setPosition(0f, 400f)
                            image(assets[TextureAtlasAsset.DRAWABLE.descriptor].findRegion(AtlasRegion.L02_STONES_PLANT.region)) {

                            }
                            container {
                                isTransform = true
                                rotation = 20f
                                label("LEVEL 2", "primary-gw") {
                                    setPosition(0f, 200f, Align.top)
                                    setFontScale(SCALE_FONT_BIG)
                                }
                            }

                        }

                        label("MULAI", "primary-gw").setPosition(parent.width / 2f, 30f)
                    }
                }
                validate()
                scrollPercentY = 1f
            }
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
