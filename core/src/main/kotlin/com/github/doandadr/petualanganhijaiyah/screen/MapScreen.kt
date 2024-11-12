package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.ray3k.stripe.FreeTypeSkin
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.actors
import ktx.scene2d.table
import ktx.style.defaultStyle

private val LOG = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {

    override fun show() {
        LOG.debug { "Map Screen is shown" }

        setupUI()
    }

    private fun setupUI() {
        val bgMap = assets[TextureAsset.MAP.descriptor]

        stage.actors {
            table {
                defaults().fillX().expandX()
                debug()
                background(TextureRegionDrawable(bgMap))

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
