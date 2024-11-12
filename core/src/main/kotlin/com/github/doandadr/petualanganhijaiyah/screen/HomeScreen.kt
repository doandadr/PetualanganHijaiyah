package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.ray3k.stripe.FreeTypeSkin
import ktx.app.clearScreen
import ktx.log.logger
import ktx.scene2d.*

private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        LOG.debug { "Home Screen is shown" }
    }

    override fun render(delta: Float) {
        val skin = FreeTypeSkin(Gdx.files.internal("skin/skin.json"))

        Scene2DSkin.defaultSkin = skin

        val atlas = assets[TextureAtlasAsset.DRAWABLE.descriptor]
        val pic = atlas.findRegion("board")
        val bgHome = assets[TextureAsset.HOME.descriptor]
        stage.actors {
            // Root actor added directly to the stage - a table:
            table {
                // Table settings:
                setFillParent(true)
                background(TextureRegionDrawable(bgHome))
                // Table children:
                verticalGroup {
                    horizontalGroup {
                        label("PETUALANGAN\nHIJAIYAH", "primary-gw"){
                            width = 100f
                        }
                        button("coin")
                        button("book")
                    }
                    verticalGroup {
                        textButton("MULAI", "board") {
                            isTransform = true
                            rotation = 10f
                        }
                        textButton("PENGATURAN", "board-s")
                        textButton("KELUAR", "board-s") {
                            isTransform = true
                            scaleX = 0.8f
                            scaleY = 0.8f
                        }
                    }
                    label("Selamat\nDatang!", "primary-gw")
                    textButton("AZHARA", "sign")
                }
//                label("بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيْمِ", style = "arabic")
            }
        }

        Gdx.input.inputProcessor = stage

        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        stage.run {
            viewport.apply()
            act()
            draw()
        }
    }

    override fun hide() {

    }

    private fun doSomething() {
        TODO("Not yet implemented")
    }

}
