package com.github.doandadr.petualanganhijaiyah.data

import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset

enum class PrefKey {
    USER_NAME,
    USER_GENDER,
    SCORE_TOTAL,
    COINS,
    LEVEL_COMPLETE_1,
    LEVEL_COMPLETE_2,
    LEVEL_COMPLETE_3,
    LEVEL_COMPLETE_4,
    LEVEL_COMPLETE_5,
    LEVEL_COMPLETE_6,
    LEVEL_COMPLETE_7,
    LEVEL_COMPLETE_8,
    LEVEL_COMPLETE_9,
    LEVEL_SCORE_1,
    LEVEL_SCORE_2,
    LEVEL_SCORE_3,
    LEVEL_SCORE_4,
    LEVEL_SCORE_5,
    LEVEL_SCORE_6,
    LEVEL_SCORE_7,
    LEVEL_SCORE_8,
    LEVEL_SCORE_9,
    TT_HOME_PRACTICE_BOOK,
    TT_HOME_SETTINGS,
    TT_PRACTICE,
    TT_MAP_START,
    TT_STAGE_LEVEL1,
}

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
    ALIF("f01-alif.png", "d01-alif.png", SoundAsset.V01_ALIF),
    BA("f02-ba.png", "d02-ba.png", SoundAsset.V02_BA),
    TA("f03-ta.png", "d03-ta.png", SoundAsset.V03_TA),
    TSA("f04-tsa.png", "d04-tsa.png", SoundAsset.V04_TSA),
    JIM("f05-jim.png", "d05-jim.png", SoundAsset.V05_JIM),
    KHA("f06-kha.png", "d06-kha.png", SoundAsset.V06_KHA),
    KHO("f07-kho.png", "d07-kho.png", SoundAsset.V07_KHO),
    DAL("f08-dal.png", "d08-dal.png", SoundAsset.V08_DAL),
    DZAL("f09-dzal.png", "d09-dzal.png", SoundAsset.V09_DZAL),
    RA("f10-ra.png", "d10-ra.png", SoundAsset.V10_RA),
    ZA("f11-za.png", "d11-za.png", SoundAsset.V11_ZA),
    SIN("f12-sin.png", "d12-sin.png", SoundAsset.V12_SIN),
    SYIN("f13-syin.png", "d13-syin.png", SoundAsset.V13_SYIN),
    SHOD("f14-shod.png", "d14-shod.png", SoundAsset.V14_SHOD),
    DHOD("f15-dhod.png", "d15-dhod.png", SoundAsset.V15_DHOD),
    THO("f16-tho.png", "d16-tho.png", SoundAsset.V16_THO),
    DHO("f17-dho.png", "d17-dho.png", SoundAsset.V17_DHO),
    AIN("f18-ain.png", "d18-ain.png", SoundAsset.V18_AIN),
    GHOIN("f19-ghoin.png", "d19-ghoin.png", SoundAsset.V19_GHOIN),
    FA("f20-fa.png", "d20-fa.png", SoundAsset.V20_FA),
    QOF("f21-qof.png", "d21-qof.png", SoundAsset.V21_QOF),
    KAF("f22-kaf.png", "d22-kaf.png", SoundAsset.V22_KAF),
    LAM("f23-lam.png", "d23-lam.png", SoundAsset.V23_LAM),
    MIM("f24-mim.png", "d24-mim.png", SoundAsset.V24_MIM),
    NUN("f25-nun.png", "d25-nun.png", SoundAsset.V25_NUN),
    WAWU("f26-wawu.png", "d26-wawu.png", SoundAsset.V26_WAWU),
    HA("f27-ha.png", "d27-ha.png", SoundAsset.V27_HA),
    YA("f28-ya.png", "d28-ya.png", SoundAsset.V28_YA),
}



