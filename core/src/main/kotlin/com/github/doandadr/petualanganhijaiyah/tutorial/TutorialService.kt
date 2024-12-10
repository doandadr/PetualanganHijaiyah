package com.github.doandadr.petualanganhijaiyah.tutorial

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.scene2d.KTable
import ktx.scene2d.Scene2DSkin


enum class TutorialTypes(key: String) {
    NONE("none"),
    // home
    HOME_PRACTICE("practice"),
    HOME_SETTINGS("settings"),
    HOME_NAME_BUTTON("name-button"),



    // practice


}

class TutorialService {
}

class TutorialWidget(
    skin: Skin = Scene2DSkin.defaultSkin
): Table(skin), KTable {

}
