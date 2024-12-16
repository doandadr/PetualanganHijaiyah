package com.github.doandadr.petualanganhijaiyah.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.tensorflow.SavedModelBundle

enum class TextureAtlasAsset(
    fileName: String,
    directory: String = "graphics/atlas",
    val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor("$directory/$fileName", TextureAtlas::class.java)
) {
    HIJAIYAH("hijaiyah.atlas"),

}

enum class MlModelAsset(
    fileName: String,
    directory: String = "ml",
    val descriptor: AssetDescriptor<SavedModelBundle> = AssetDescriptor("$directory/$fileName", SavedModelBundle::class.java)
) {
    SAVED_MODEL("saved_model.pb"),
}
