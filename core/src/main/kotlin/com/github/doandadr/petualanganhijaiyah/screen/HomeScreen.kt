package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.ray3k.stripe.FreeTypeSkin
import ktx.app.clearScreen
import ktx.log.logger
import ktx.scene2d.*


private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : Screen(game) {

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
                    textButton("MULAI", "board")
                    textButton("MULAI", "board")
                    textButton("MULAI", "board")
                }
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
}
