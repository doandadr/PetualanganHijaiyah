package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_HIJAIYAH
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_HIJAIYAH_MEDIUM
import ktx.actors.onTouchEvent
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

class HijaiyahBox(
    var hijaiyah: Hijaiyah,
    size: HijaiyahSize,
    assets: AssetStorage
) : Table(), KTable {
    val frame: Button
    val image: Image
    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]
    private val sizeValue: Float = when (size) {
        HijaiyahSize.SMALL -> SIZE_HIJAIYAH_MEDIUM
        HijaiyahSize.MEDIUM -> SIZE_HIJAIYAH_MEDIUM
    }
    init {
        stack {
            it.prefSize(this@HijaiyahBox.sizeValue)

            this@HijaiyahBox.frame = button(Buttons.HIJAIYAH.style) {
                isTransform = true
                setOrigin(Align.center)
                setScale(SCALE_BTN_HIJAIYAH)
            }
            this@HijaiyahBox.image = image {
                setScaling(Scaling.fit)
                touchable = Touchable.disabled
            }
        }

        updateLetter(hijaiyah)

        frame.onTouchEvent(
            onDown = {_ ->
                // TODO change style to orange
            },
            onUp = {
                // TODO handle answer
            }
        )
    }

    fun updateLetter(letter: Hijaiyah) {
        hijaiyah = letter
        image.drawable = TextureRegionDrawable(textAtlas.findRegion(hijaiyah.image))
    }

    enum class HijaiyahSize {
        SMALL,
        MEDIUM,
    }
}

inline fun <S> KWidget<S>.hijaiyahBox(
    hijaiyah: Hijaiyah,
    size: HijaiyahBox.HijaiyahSize,
    assets: AssetStorage,
    init: HijaiyahBox.(S) -> Unit = {}
) = actor(
    HijaiyahBox(
        hijaiyah,
        size,
        assets,
    ), init
)
