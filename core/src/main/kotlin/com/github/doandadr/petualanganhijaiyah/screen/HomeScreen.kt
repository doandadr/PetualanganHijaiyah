package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
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
                        setScale(1.2f)
                    }.onClick {
                        game.setScreen<MapScreen>()
                    }
                    textButton("PENGATURAN", "board")
                    textButton("KELUAR", "board") {
                        isTransform = true
                        setScale(0.8f)
                    }
                }
                row()
                stack {
                    textButton("AZHARA", "sign")
                    label("Selamat\nDatang!", "primary-gw") {
                        top
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
