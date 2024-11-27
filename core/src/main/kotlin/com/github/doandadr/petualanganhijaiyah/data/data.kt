package com.github.doandadr.petualanganhijaiyah.data

import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset

enum class LevelImg(
    val image: String,
) {
    LEVEL_1("l01-rock-boulder.png"),
    LEVEL_2("l02-stones-plant.png"),
    LEVEL_3("l03-cat-bigrock.png"),
    LEVEL_4("l04-grass-twig.png"),
    LEVEL_5("l05-pond.png"),
    LEVEL_6("l06-bigstone.png"),
    LEVEL_7("l07-rock-plants.png"),
    LEVEL_8("l08-cliff.png"),
    LEVEL_9("l09-cactus-stone.png"),
}

// A level can only have of one type of stage
// A level consists of N amount of stages
// Stages of the level's type is taken randomly from a list of stage data (question and answer), OR from random
// A level consists of N amount of stars/score
// Both stars and score are dictated by a timer/how many wrongs per stage/usage of passes or coins
// Maximum score of 100, stars is (20 - 1 star, 50 - 2 star, 80 - 3 star)
// Passing once
// A stage has different types (match line etc)
// TODO stage def

// TODO level data (type, )

enum class Hijaiyah(
    val image: String,
    val drawImg: String,
    val audio: SoundAsset,
) {
    ALIF("f01-alif", "d01-alif", SoundAsset.V01_ALIF),
    BA("f02-ba", "d02-ba", SoundAsset.V02_BA),
    TA("f03-ta", "d03-ta", SoundAsset.V03_TA),
    TSA("f04-tsa", "d04-tsa", SoundAsset.V04_TSA),
    JIM("f05-jim", "d05-jim", SoundAsset.V05_JIM),
    KHA("f06-kha", "d06-kha", SoundAsset.V06_KHA),
    KHO("f07-kho", "d07-kho", SoundAsset.V07_KHO),
    DAL("f08-dal", "d08-dal", SoundAsset.V08_DAL),
    DZAL("f09-dzal", "d09-dzal", SoundAsset.V09_DZAL),
    RA("f10-ra", "d10-ra", SoundAsset.V10_RA),
    ZA("f11-za", "d11-za", SoundAsset.V11_ZA),
    SIN("f12-sin", "d12-sin", SoundAsset.V12_SIN),
    SYIN("f13-syin", "d13-syin", SoundAsset.V13_SYIN),
    SHOD("f14-shod", "d14-shod", SoundAsset.V14_SHOD),
    DHOD("f15-dhod", "d15-dhod", SoundAsset.V15_DHOD),
    THO("f16-tho", "d16-tho", SoundAsset.V16_THO),
    DHO("f17-dho", "d17-dho", SoundAsset.V17_DHO),
    AIN("f18-ain", "d18-ain", SoundAsset.V18_AIN),
    GHOIN("f19-ghoin", "d19-ghoin", SoundAsset.V19_GHOIN),
    FA("f20-fa", "d20-fa", SoundAsset.V20_FA),
    QOF("f21-qof", "d21-qof", SoundAsset.V21_QOF),
    KAF("f22-kaf", "d22-kaf", SoundAsset.V22_KAF),
    LAM("f23-lam", "d23-lam", SoundAsset.V23_LAM),
    MIM("f24-mim", "d24-mim", SoundAsset.V24_MIM),
    NUN("f25-nun", "d25-nun", SoundAsset.V25_NUN),
    WAWU("f26-wawu", "d26-wawu", SoundAsset.V26_WAWU),
    HA("f27-ha", "d27-ha", SoundAsset.V27_HA),
    YA("f28-ya", "d28-ya", SoundAsset.V28_YA),
}



