package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.LabelStyles
import com.github.doandadr.petualanganhijaiyah.screen.SCALE_FONT_BIG
import ktx.scene2d.*

enum class LevelState{
    INACCESSIBLE,
    AVAILABLE,
    PASSED
}

class Level(
    private val skin: Skin,
    private var label: Label,
    private val container: Container<Label>,
    val button: Button,
) : WidgetGroup(button, container), KGroup {
    init {
        // TODO set state based on save data
        setState(LevelState.INACCESSIBLE)
    }

    fun setPositions(x: Float = 0f, y: Float = 0f, textX: Float = 0f, textY: Float = 0f) {
        container.setPosition(textX, textY)
        setPosition(x, y)
    }

    private fun setTextScale(scale: Float) {
        label.setFontScale(scale)
    }

    fun setTextRotation(degrees: Float) {
        container.isTransform = true
        container.setOrigin(Align.center)
        container.rotation = degrees
    }

    fun setButtonScale(scale:Float) {
        button.isTransform = true
        button.setOrigin(Align.center)
        button.setScale(scale)
    }

    fun setButtonRotation(degrees:Float) {
        button.rotation = degrees
    }

    fun setState(state: LevelState) {
        label = when(state) {
            LevelState.INACCESSIBLE -> Label(label.text, skin, LabelStyles.PRIMARY_GREY_L.styleName)
            LevelState.AVAILABLE -> Label(label.text, skin, LabelStyles.PRIMARY_GREEN_L.styleName)
            LevelState.PASSED -> Label(label.text, skin, LabelStyles.PRIMARY_YELLOW_L.styleName)
        }
        setTextScale(SCALE_FONT_BIG)
    }
}

inline fun <S> KWidget<S>.levelButton(
    labelText: String,
    buttonStyle: String,
    skin: Skin = Scene2DSkin.defaultSkin,
    labelLevel: Label = Label(labelText, skin, LabelStyles.PRIMARY_GREY_L.styleName),
    buttonLevel: Button = Button(skin, buttonStyle),
    containerLevel: Container<Label> = Container(labelLevel),
    init: Level.(S) -> Unit = {}
) = actor(
    Level(
        skin,
        labelLevel,
        containerLevel,
        buttonLevel,
    ), init
)
