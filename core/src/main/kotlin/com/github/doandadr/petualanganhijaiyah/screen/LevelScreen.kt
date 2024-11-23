package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.LabelStyles
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.util.centerX
import com.github.doandadr.petualanganhijaiyah.util.centerY
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

private val log = logger<LevelScreen>()

class LevelScreen(game: Main): BaseScreen(game) {
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun show() {
        log.debug { "Stage Screen is shown" }
        setupUI()
        // TODO play music
//        audioService.play(MusicAsset.FIELD, 0.2f)
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        val bgStage = assets[TextureAsset.FOREST_1.descriptor]
        val bgStage2 = assets[TextureAsset.FOREST_2.descriptor]
        val userName = "AZHARA" // TODO take from preferences
        val levelName = "LEVEL 1" // TODO take from input from mapscreen

        stage.isDebugAll = true
        stage.actors {
            table {
                setFillParent(true)
                background(TextureRegionDrawable(bgStage))
                align(Align.bottomLeft)

                floatingGroup {
                    setFillParent(true)
                    // TODO custom StageBoard widget
                    // TODO level name
                    label(levelName, LabelStyles.PRIMARY_GREEN_L.styleName) {
                        setAlignment(Align.center)
                        setPosition(0f, 1000f, )
                    }
                    // TODO sign name
                    label(userName, LabelStyles.SIGN_NAME.styleName) {
                        setAlignment(Align.topRight)
                        setPosition(-20f, -20f)
                    }
                    // TODO hint button and functionalities

                }

                // TODO data management and event management
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
