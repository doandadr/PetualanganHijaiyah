package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_BIG
import ktx.scene2d.KGroup
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor

class LevelButton(
    levelText: String,
    buttonStyle: String,
    private val skin: Skin = Scene2DSkin.defaultSkin,
    private val label: Label = Label(levelText, skin, Labels.PRIMARY_GREY_WHITE_BORDER.style),
    val button: Button = Button(skin, buttonStyle),
    val text: Container<Label> = Container(label),
    val dots: LevelDots = LevelDots(skin),
    val stars: Stars = Stars(skin),
) : WidgetGroup(button, text, dots, stars), KGroup {
    init {
        // TODO set state based on save data
        setState(LevelButtonState.INACCESSIBLE)
        label.setFontScale(SCALE_FONT_BIG)
    }

    fun rotateText(degrees: Float) {
        text.isTransform = true
        text.setOrigin(Align.center)
        text.rotation = degrees
    }

    fun setState(state: LevelButtonState) {
        if (state == LevelButtonState.HIDDEN) {
            isVisible = false
            return
        }
        isVisible = true

        when (state) {
            LevelButtonState.INACCESSIBLE -> label.style =
                skin.get(Labels.PRIMARY_GREY_WHITE_BORDER.style, LabelStyle::class.java)

            LevelButtonState.AVAILABLE -> label.style =
                skin.get(Labels.PRIMARY_GREEN_WHITE_BORDER.style, LabelStyle::class.java)

            LevelButtonState.PASSED -> label.style =
                skin.get(Labels.PRIMARY_ORANGE_WHITE_BORDER.style, LabelStyle::class.java)

            else -> {}
        }
        when (state) {
            LevelButtonState.INACCESSIBLE -> dots.setState(LevelDots.DotState.INACCESSIBLE)
            LevelButtonState.AVAILABLE -> dots.setState(LevelDots.DotState.AVAILABLE)
            LevelButtonState.PASSED -> dots.setState(LevelDots.DotState.PASSED)
            else -> {}
        }
    }

    enum class LevelButtonState {
        HIDDEN,
        INACCESSIBLE,
        AVAILABLE,
        PASSED
    }
}

inline fun <S> KWidget<S>.levelButton(
    labelText: String,
    buttonStyle: String,
    init: LevelButton.(S) -> Unit = {}
) = actor(
    LevelButton(
        labelText,
        buttonStyle,
    ), init
)
