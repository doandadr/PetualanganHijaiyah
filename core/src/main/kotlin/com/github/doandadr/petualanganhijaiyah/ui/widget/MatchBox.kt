package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.doandadr.petualanganhijaiyah.asset.Drawables
import com.github.doandadr.petualanganhijaiyah.asset.Hijaiyah
import ktx.actors.alpha
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.KTable
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.container
import ktx.scene2d.image

class MatchBox(
    val hijaiyah: Hijaiyah,
    private val state: State,
    private val assets: AssetStorage,
    skin: Skin = Scene2DSkin.defaultSkin,
) : Table(skin), KTable {
    val box: HijaiyahBox
    val rightCircle:  Container<Actor>
    val leftCircle: Container<Actor>
    val leftDot: Image
    val rightDot: Image

    init {
        this@MatchBox.leftCircle = container {
            background = skin.getDrawable(Drawables.CIRCLE_ORANGE.drawable)
            isVisible = this@MatchBox.state == State.RIGHT
            it.padRight(PADDING_IN)
            this@MatchBox.leftDot = image(Drawables.CIRCLE_BRUSH.drawable) {
                alpha = 0.1f
                log.debug { "leftDot isTouchable $touchable" }
            }
            log.debug { "leftCircle isTouchable $touchable" }
        }
        this@MatchBox.box = hijaiyahBox(
            hijaiyah,
            HijaiyahBox.Size.SMALL,
            assets
        ) {
            toBack()
        }
        this@MatchBox.rightCircle = container{
            background = skin.getDrawable(Drawables.CIRCLE_GREEN.drawable)
            isVisible = this@MatchBox.state == State.LEFT
//            touchable = Touchable.enabled
            log.debug { "rightCircle isTouchable $touchable" }
            it.padLeft(PADDING_IN)
            this@MatchBox.rightDot = image(Drawables.CIRCLE_BRUSH.drawable) {
                alpha = 1f
//                touchable
                log.debug { "rightDot isTouchable $touchable" }
            }
        }

//        leftDot.userObject = this@MatchBox
//        rightDot.userObject = this@MatchBox
    }

    enum class State {
        LEFT,
        RIGHT
    }

    companion object {
        private val log = logger<MatchBox>()

        private const val PADDING_IN = -10f
    }
}

class PixmapDemo() {
    lateinit var pixmap : Pixmap
    lateinit var texture: Texture
    fun initPixmap() {
        pixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGBA8888)
        texture = Texture(pixmap)
    }

    fun touchDraggedSnippet(screenX: Int,  screenY:Int) {
        pixmap.setColor(Color.BLACK)
//        pixmap.drawLine()
        pixmap.fillCircle(screenX, screenY - (Gdx.graphics.height - pixmap.height), 5)
    }

}

