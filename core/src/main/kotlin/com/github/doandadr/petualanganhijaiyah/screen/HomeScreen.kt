package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.ray3k.stripe.FreeTypeSkin
import ktx.actors.onClick
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.async.newAsyncContext
import ktx.log.logger
import ktx.scene2d.*
import ktx.style.skin

private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        LOG.debug { "Home Screen is shown" }

        setupUI()
    }

    private fun setupUI() {
        val bgHome = assets[TextureAsset.HOME.descriptor]
        stage.actors {
            // Root actor added directly to the stage - a table:
            table {
                // Table settings:
                setFillParent(true)
                debug()
                background(TextureRegionDrawable(bgHome))

                // Table children:
                horizontalGroup {
                    center()
                    label("PETUALANGAN\nHIJAIYAH", "primary-gw")
                    button("coin")
                    button("book")
                }
                row()
                verticalGroup {
                    center()
                    space(10f)
                    textButton("MULAI", "board") {
                        isTransform = true
                        rotation = 10f
                        setScale(1.1f)
                    }.onClick {
                        game.setScreen<MapScreen>()
                    }
                    textButton("PENGATURAN", "board-s")
                    textButton("KELUAR", "board-s") {
                        isTransform = true
                        setScale(0.9f)
                    }
                }
                row()
                container {
                    padTop(30f)
                    stack {
                        textButton("AZHARA", "sign")
                        label("Selamat\nDatang!", "primary-gw") {
                            setAlignment(Align.top)
                        }
                    }
                }
//                label("بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيْمِ", style = "arabic")
            }
        }

        Gdx.input.inputProcessor = stage
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
