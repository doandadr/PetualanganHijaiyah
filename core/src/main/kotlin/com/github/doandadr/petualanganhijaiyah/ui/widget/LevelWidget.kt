package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import ktx.scene2d.*

class LevelWidget(
    skin: Skin,
    val labelLevel: Label,
    val buttonLevel: Button,
    val container: Container<Label>,
) : WidgetGroup(buttonLevel, container), KGroup {
    // TODO handle star graphics

    fun setPositions(x: Float = 0f, y: Float = 0f, textX: Float = 0f, textY: Float = 0f) {
        container.setPosition(textX, textY)
        setPosition(x, y)
    }

    fun setTextScale(scale: Float) {
        labelLevel.setFontScale(scale)
    }

    fun setTextRotation(degrees: Float) {
        container.isTransform = true
        container.setOrigin(Align.center)
        container.rotation = degrees
    }

    fun setButtonScale(scale:Float) {
        buttonLevel.isTransform = true
        buttonLevel.setOrigin(Align.center)
        buttonLevel.setScale(scale)
    }

    fun setButtonRotation(degrees:Float) {
        buttonLevel.rotation = degrees
    }
}

inline fun <S> KWidget<S>.levelWidget(
    labelText: String,
    labelStyle: String,
    buttonStyle: String,
    skin: Skin = Scene2DSkin.defaultSkin,
    labelLevel: Label = Label(labelText, skin, labelStyle),
    buttonLevel: Button = Button(skin, buttonStyle),
    containerLevel: Container<Label> = Container(labelLevel),
    init: LevelWidget.(S) -> Unit = {}
) = actor(
    LevelWidget(
        skin,
        labelLevel,
        buttonLevel,
        containerLevel
    ), init
)
