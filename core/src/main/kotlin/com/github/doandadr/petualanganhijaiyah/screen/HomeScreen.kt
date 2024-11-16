package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.ray3k.stripe.FreeTypeSkin
import ktx.actors.centerPosition
import ktx.actors.onClick
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.async.newAsyncContext
import ktx.log.debug
import ktx.log.logger
import ktx.scene2d.*
import ktx.style.skin

const val BUTTON_PADDING = 20f
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

        setupUI()
    }

    private fun setupUI() {
        val bgHome = assets[TextureAsset.HOME.descriptor]
        stage.isDebugAll = true
        stage.actors {
            // Background
            image(bgHome)

            horizontalGroup {
                setFillParent(true)
                top()
                expand()
                container {
//                    align(Align.topLeft)/

                    label("PETUALANGAN\nHIJAIYAH", "primary-gw")
                }
//                container {
//                    align(Align.topRight)
                    button("coin")
//                }
//                container {
///                    align(Align.topRight)
                    button("book")
//                }
            }
            verticalGroup {
                setFillParent(true)
                center()
                space(10f)
                padBottom(240f)
                textButton("MULAI", "board") {
                    isTransform = true
                    rotation = 10f
                    centerPosition(parent.width, parent.height)
                    setScale(LARGE_BTN_SCALE)
                    pad(BUTTON_PADDING)
                    onClick {
                        game.setScreen<MapScreen>()
                    }
                }
                textButton("PENGATURAN", "board-s") {
                    centerPosition(parent.width, parent.height)
                }
                textButton("KELUAR", "board-s") {
                    isTransform = true
                    centerPosition(parent.width, parent.height)
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
                bottom()

                label("Selamat\nDatang!", "primary-gw") {
                    setAlignment(Align.top)
                }
                textButton("AZHARA", "sign")
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
