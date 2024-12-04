package com.github.doandadr.petualanganhijaiyah.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Music

enum class MusicAsset(
    fileName: String,
    val volume: Float = 0.5f,
    directory: String = "audio/music",
    val descriptor: AssetDescriptor<Music> = AssetDescriptor("$directory/$fileName", Music::class.java)
) {
    DESERT("desert.mp3"),
    DESERT_ALT("desert-2.mp3"),
    FIELD("forest.mp3"),
    HOME("home.mp3", 0.5f),
    MAP("map.mp3"),
    SNOW("snow.mp3"),
    VICTORY("victory.mp3"),
}
