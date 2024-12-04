package com.github.doandadr.petualanganhijaiyah.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture

enum class TextureAsset(
    val fileName:String,
    directory: String = "graphics/bg",
    val descriptor: AssetDescriptor<Texture> = AssetDescriptor("$directory/$fileName", Texture::class.java)
) {
    DESERT("bg-desert.png"),
    FIELD("bg-field.png"),
    FINISH("bg-finish.png"),
    HOME("bg-home.png"),
    DIM("bg-dim.png"),
    STAGE("bg-stage.png"),
    START("bg-start.png"),
    MAP("bg-map.png"),
    SPOOKY_1("bg-spooky1.jpg"),
    SPOOKY_2("bg-spooky2.jpg"),
    FOREST_1("bg-forest1.jpg"),
    FOREST_2("bg-forest2.jpg"),
    DESERT_1("bg-desert1.jpg"),
    DESERT_2("bg-desert2.jpg"),
    SNOW_1("bg-snow1.jpg"),
    SNOW_2("bg-snow2.jpg"),
    MOUNTAIN_1("bg-mountain1.jpg"),
    MOUNTAIN_2("bg-mountain2.jpg"),
}

fun dumm() {
    val backgroundIdx : Int
    backgroundIdx = TextureAsset.HOME.ordinal

    val background = TextureAsset.entries[backgroundIdx]
}
