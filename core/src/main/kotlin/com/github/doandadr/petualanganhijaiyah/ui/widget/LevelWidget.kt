package com.github.doandadr.petualanganhijaiyah.ui.widget

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import ktx.scene2d.KGroup
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actor

class LevelWidget(
    // skin
    skin: Skin,
    // label
    val labelLevel: Label,
    // images
    val imageLevel: Image,
    // offsets and rotations
) : WidgetGroup(labelLevel, imageLevel), KGroup {
    val labelOffsetX: Float = 0.0f
    val labelOffsetY: Float = 0.0f
    val imageOffsetX: Float = 0.0f
    val imageOffsetY: Float = 0.0f
    val labelScale: Float = 0.0f
    val imageScale:Float = 0.0f

    init {
        isTransform = true
//        labelLevel.setPosition()
    }

//    fun setLabelOffset()

}

inline fun <S> KWidget<S>.levelWidget(
    skin: Skin = Scene2DSkin.defaultSkin
    ,
    labelLevel: Label,
    imageLevel: Image,
    init: LevelWidget.(S) -> Unit = {}
) = actor(LevelWidget(
    skin,
    labelLevel,
    imageLevel,
), init)
