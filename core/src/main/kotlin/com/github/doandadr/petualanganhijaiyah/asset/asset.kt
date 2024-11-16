package com.github.doandadr.petualanganhijaiyah.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

enum class SoundAsset(
    fileName: String,
    directory: String = "audio/sound",
    val volumeScale: Float = 1f,
    val descriptor: AssetDescriptor<Sound> = AssetDescriptor("$directory/$fileName", Sound::class.java)
) {
    CLICK_BUTTON("button-click.ogg"),
    COIN_TRING("coin.ogg"),
    COMPLETE("complete-chime.ogg"),
    CORRECT_DING("correct.ogg"),
    CORRECT_BLING("correct-2.ogg"),
    INCORRECT("incorrect.ogg"),
    CLICK_MOUSE("mouse-click.ogg"),
    FAILURE("negative-beeps.ogg"),
    POP_LOW("plop.ogg"),
    POP_HIGH("pop.ogg"),
    STRETCH("pull-stretch.ogg"),
    DRAW_HEAVY("draw.ogg"),
    DRAW_LIGHT("scribble.ogg"),
    CHEER_BIG("crowd-cheer.ogg"),
    CHEER_SMALL("small-crowd.ogg"),
    CHIME_HALF("up-chime-1.ogg"),
    CHIME_FULL("up-chime-2.ogg"),
    CHIME_HIGH("up-chime-4.ogg"),
    FANFARE_ORCHESTRA("fanfare-1.ogg"),
    FANFARE_TADA("fanfare-2.ogg"),
    V01_ALIF("v01-alif.ogg"),
    V02_BA("v02-ba.ogg"),
    V03_TA("v03-ta.ogg"),
    V04_TSA("v04-tsa.ogg"),
    V05_JIM("v05-jim.ogg"),
    V06_KHA("v06-kha.ogg"),
    V07_KHO("v07-kho.ogg"),
    V08_DAL("v08-dal.ogg"),
    V09_DZAL("v09-dzal.ogg"),
    V10_RA("v10-ra.ogg"),
    V11_ZA("v11-za.ogg"),
    V12_SIN("v12-sin.ogg"),
    V13_SYIN("v13-syin.ogg"),
    V14_SHOD("v14-shod.ogg"),
    V15_DHOD("v15-dhod.ogg"),
    V16_THO("v16-tho.ogg"),
    V17_DHO("v17-dho.ogg"),
    V18_AIN("v18-ain.ogg"),
    V19_GHOIN("v19-ghoin.ogg"),
    V20_FA("v20-fa.ogg"),
    V21_QOF("v21-qof.ogg"),
    V22_KAF("v22-kaf.ogg"),
    V23_LAM("v23-lam.ogg"),
    V24_MIM("v24-mim.ogg"),
    V25_NUN("v25-nun.ogg"),
    V26_WAWU("v26-wawu.ogg"),
    V27_HA("v27-ha.ogg"),
    V28_YA("v28-ya.ogg"),
    V29_LAMALIF("v29-lamalif.ogg"),
    V30_HAMZAH("v30-hamzah.ogg"),
}

enum class MusicAsset(
    fileName: String,
    directory: String = "audio/music",
    val volumeScale: Float = 0.15f,
    val descriptor: AssetDescriptor<Music> = AssetDescriptor("$directory/$fileName", Music::class.java)
) {
    DESERT("desert.mp3"),
    DESERT_ALT("desert-2.mp3"),
    FIELD("forest.mp3"),
    HOME("home.mp3"),
    MAP("map.mp3"),
    SNOW("snow.mp3"),
    VICTORY("victory.mp3"),
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
