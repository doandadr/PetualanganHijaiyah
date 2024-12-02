package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.asset.Buttons
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.github.doandadr.petualanganhijaiyah.data.Hijaiyah
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_HIJAIYAH
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_HIJAIYAH_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_HIJAIYAH_SMALL
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

class HijaiyahBox(
    var hijaiyah: Hijaiyah,
    size: Size,
    assets: AssetStorage,
    skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private val boxStack: Stack
    private var hijaiyahText: Label
    private var frame: Button
    private var image: Image
    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]
    private val sizeValue: Float = when (size) {
        Size.SMALL -> SIZE_HIJAIYAH_SMALL
        Size.MEDIUM -> SIZE_HIJAIYAH_MEDIUM
    }
    private var hijaiyahState: State = State.DEFAULT

    init {
        this@HijaiyahBox.boxStack = stack {
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
            this@HijaiyahBox.hijaiyahText = label("", Labels.SECONDARY_BORDER.style) {
                setAlignment(Align.center)
                touchable = Touchable.disabled
            }
        }

        setState(State.DEFAULT)
        updateLetter(hijaiyah)
    }

    fun updateLetter(letter: Hijaiyah) {
        hijaiyah = letter
        image.drawable = TextureRegionDrawable(textAtlas.findRegion(hijaiyah.image))
    }

    enum class Size {
        SMALL,
        MEDIUM,
    }

    enum class State {
        DEFAULT,
        TEXT,
        CORRECT,
        INCORRECT,
        DROP
    }

    fun setState(state: State) {
        when (state) {
            State.DEFAULT -> {
                hijaiyahText.isVisible = false
                image.isVisible = true
            }

            State.TEXT -> {
                hijaiyahText.isVisible = true
                image.isVisible = false
                hijaiyahText.setText(hijaiyah.name.uppercase())
            }

            State.DROP -> {
                hijaiyahText.isVisible = true
                image.isVisible = false
                frame.style = skin.get(Buttons.HIJAIYAH_DROP.style,ButtonStyle::class.java)
                hijaiyahText.setText(hijaiyah.name.uppercase())
            }

            State.CORRECT -> {
                frame.apply {
                    style = skin.get(Buttons.HIJAIYAH_STATE.style, ButtonStyle::class.java)
                    isDisabled = false
                }
            }

            State.INCORRECT -> {
                frame.apply {
                    style = skin.get(Buttons.HIJAIYAH_STATE.style, ButtonStyle::class.java)
                    isDisabled = true
                }
            }
        }
    }
}

inline fun <S> KWidget<S>.hijaiyahBox(
    hijaiyah: Hijaiyah,
    size: HijaiyahBox.Size,
    assets: AssetStorage,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: HijaiyahBox.(S) -> Unit = {}
) = actor(
    HijaiyahBox(
        hijaiyah,
        size,
        assets,
        skin,
    ), init
)
