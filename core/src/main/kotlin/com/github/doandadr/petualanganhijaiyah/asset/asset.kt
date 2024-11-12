package com.github.doandadr.petualanganhijaiyah.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

// TODO SoundAsset
enum class SoundAsset(
    fileName: String,
    directory: String = "audio/sound",
    val descriptor: AssetDescriptor<Sound> = AssetDescriptor("$directory/$fileName", Sound::class.java)
) {

}

// TODO MusicAsset
enum class MusicAsset(
    fileName: String,
    directory: String = "audio/music",
    val descriptor: AssetDescriptor<Music> = AssetDescriptor("$directory/$fileName", Music::class.java)
) {

}

enum class TextureAsset(
    fileName:String,
    directory: String = "graphics/bg",
    val descriptor: AssetDescriptor<Texture> = AssetDescriptor("$directory/$fileName", Texture::class.java)
) {
    DESERT("bg-desert.png"),
    FIELD("bg-field.png"),
    FINISH("bg-finish.png"),
    HOME("bg-home.png"),
    STAGE("bg-stage.png"),
    START("bg-start.png"),
    MAP("bg-map.png")
}

enum class TextureAtlasAsset(
    val isSkinAtlas: Boolean,
    fileName: String,
    directory: String = "graphics/atlas",
    val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor("$directory/$fileName", TextureAtlas::class.java)
) {
    DRAWABLE(true, "drawable.atlas"),
    NINEPATCH(true, "ninepatch.atlas"),
    LETTER(true, "hijaiyah.atlas"),
}

// TODO FreetypeFontAsset
enum class FreetypeFontAsset (
    fileName: String,
    directory: String = "font",
)
