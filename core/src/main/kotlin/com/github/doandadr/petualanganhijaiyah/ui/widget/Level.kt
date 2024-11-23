package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.screen.SCALE_FONT_BIG
import ktx.scene2d.*

enum class LevelState {
    INACCESSIBLE,
    AVAILABLE,
    PASSED
}

class Level(
    labelText: String,
    buttonStyle: String,
    private val skin: Skin,
    private val label: Label = Label(labelText, skin, Labels.PRIMARY_GREY_L.style),
    val button: Button = Button(skin, buttonStyle),
    val text: Container<Label> = Container(label),
    val dots: LevelDots = LevelDots(skin),
    val stars: Stars = Stars(skin),
) : WidgetGroup(button, text, dots, stars), KGroup {

    init {

        // TODO set state based on save data
        setState(LevelState.INACCESSIBLE)
        label.setFontScale(SCALE_FONT_BIG)
    }

    fun setPositions(x: Float = 0f, y: Float = 0f, textX: Float = 0f, textY: Float = 0f) {
        text.setPosition(textX, textY)
        setPosition(x, y)
    }

    fun rotateText(degrees: Float) {
        text.isTransform = true
        text.setOrigin(Align.center)
        text.rotation = degrees
    }

    fun setState(state: LevelState) {
        label.style = when (state) {
            LevelState.INACCESSIBLE -> skin.get(Labels.PRIMARY_GREY_L.style, LabelStyle::class.java)
            LevelState.AVAILABLE -> skin.get(Labels.PRIMARY_GREEN_L.style, LabelStyle::class.java)
            LevelState.PASSED -> skin.get(Labels.PRIMARY_YELLOW_L.style, LabelStyle::class.java)
        }
        when(state) {
            LevelState.INACCESSIBLE -> dots.setState(DotState.INACCESSIBLE)
            LevelState.AVAILABLE -> dots.setState(DotState.AVAILABLE)
            LevelState.PASSED -> dots.setState(DotState.PASSED)
        }
    }
}

inline fun <S> KWidget<S>.levelButton(
    labelText: String,
    buttonStyle: String,
    skin: Skin = Scene2DSkin.defaultSkin,
    init: Level.(S) -> Unit = {}
) = actor(
    Level(
        labelText,
        buttonStyle,
        skin,
    ), init
)
