package com.github.doandadr.petualanganhijaiyah.asset

interface IHijaiyah {

}

enum class Hijaiyah(
    val character: String,
    val id: String,
    val reading: String,
    val audio: SoundAsset,
    val audioFathah: SoundAsset,
    val audioKasrah: SoundAsset,
    val audioDhommah: SoundAsset,
    val image: String = "f$id-$reading",
    val drawImg: String = "d$id-$reading",
    val imageSmall: String = "sf$id-$reading",
    val imageNumber: String = "n$id-$reading",
) {
    ALIF(
        "أ",
        "01", "alif",
        SoundAsset.V01_ALIF,
        SoundAsset.V01_ALIF_A,
        SoundAsset.V01_ALIF_I,
        SoundAsset.V01_ALIF_U
    ),
    BA("ب", "02", "ba", SoundAsset.V02_BA, SoundAsset.V02_BA_A, SoundAsset.V02_BA_I, SoundAsset.V02_BA_U),
    TA("ت", "03", "ta", SoundAsset.V03_TA, SoundAsset.V03_TA_A, SoundAsset.V03_TA_I, SoundAsset.V03_TA_U),
    TSA(
        "ث",
        "04", "tsa",
        SoundAsset.V04_TSA,
        SoundAsset.V04_TSA_A,
        SoundAsset.V04_TSA_I,
        SoundAsset.V04_TSA_U
    ),
    JIM(
        "ج",
        "05", "jim",
        SoundAsset.V05_JIM,
        SoundAsset.V05_JIM_A,
        SoundAsset.V05_JIM_I,
        SoundAsset.V05_JIM_U
    ),
    KHA(
        "ح",
        "06", "kha",
        SoundAsset.V06_KHA,
        SoundAsset.V06_KHA_A,
        SoundAsset.V06_KHA_I,
        SoundAsset.V06_KHA_U
    ),
    KHO(
        "خ",
        "07", "kho",
        SoundAsset.V07_KHO,
        SoundAsset.V07_KHO_A,
        SoundAsset.V07_KHO_I,
        SoundAsset.V07_KHO_U
    ),
    DAL(
        "د",
        "08", "dal",
        SoundAsset.V08_DAL,
        SoundAsset.V08_DAL_A,
        SoundAsset.V08_DAL_I,
        SoundAsset.V08_DAL_U
    ),
    DZAL(
        "ذ",
        "09", "dzal",
        SoundAsset.V09_DZAL,
        SoundAsset.V09_DZAL_A,
        SoundAsset.V09_DZAL_I,
        SoundAsset.V09_DZAL_U
    ),
    RA("ر", "10", "ra", SoundAsset.V10_RA, SoundAsset.V10_RA_A, SoundAsset.V10_RA_I, SoundAsset.V10_RA_U),
    ZA("ز", "11", "za", SoundAsset.V11_ZA, SoundAsset.V11_ZA_A, SoundAsset.V11_ZA_I, SoundAsset.V11_ZA_U),
    SIN(
        "س",
        "12", "sin",
        SoundAsset.V12_SIN,
        SoundAsset.V12_SIN_A,
        SoundAsset.V12_SIN_I,
        SoundAsset.V12_SIN_U
    ),
    SYIN(
        "ش",
        "13", "syin",
        SoundAsset.V13_SYIN,
        SoundAsset.V13_SYIN_A,
        SoundAsset.V13_SYIN_I,
        SoundAsset.V13_SYIN_U
    ),
    SHOD(
        "ص",
        "14", "shod",
        SoundAsset.V14_SHOD,
        SoundAsset.V14_SHOD_A,
        SoundAsset.V14_SHOD_I,
        SoundAsset.V14_SHOD_U
    ),
    DHOD(
        "ض",
        "15", "dhod",
        SoundAsset.V15_DHOD,
        SoundAsset.V15_DHOD_A,
        SoundAsset.V15_DHOD_I,
        SoundAsset.V15_DHOD_U
    ),
    THO(
        "ط",
        "16", "tho",
        SoundAsset.V16_THO,
        SoundAsset.V16_THO_A,
        SoundAsset.V16_THO_I,
        SoundAsset.V16_THO_U
    ),
    DHO(
        "ظ",
        "17", "dho",
        SoundAsset.V17_DHO,
        SoundAsset.V17_DHO_A,
        SoundAsset.V17_DHO_I,
        SoundAsset.V17_DHO_U
    ),
    AIN(
        "ع",
        "18", "ain",
        SoundAsset.V18_AIN,
        SoundAsset.V18_AIN_A,
        SoundAsset.V18_AIN_I,
        SoundAsset.V18_AIN_U
    ),
    GHOIN(
        "غ",
        "19", "ghoin",
        SoundAsset.V19_GHOIN,
        SoundAsset.V19_GHOIN_A,
        SoundAsset.V19_GHOIN_I,
        SoundAsset.V19_GHOIN_U
    ),
    FA("ف", "20", "fa", SoundAsset.V20_FA, SoundAsset.V20_FA_A, SoundAsset.V20_FA_I, SoundAsset.V20_FA_U),
    QOF(
        "ق",
        "21", "qof",
        SoundAsset.V21_QOF,
        SoundAsset.V21_QOF_A,
        SoundAsset.V21_QOF_I,
        SoundAsset.V21_QOF_U
    ),
    KAF(
        "ك",
        "22", "kaf",
        SoundAsset.V22_KAF,
        SoundAsset.V22_KAF_A,
        SoundAsset.V22_KAF_I,
        SoundAsset.V22_KAF_U
    ),
    LAM(
        "ل",
        "23", "lam",
        SoundAsset.V23_LAM,
        SoundAsset.V23_LAM_A,
        SoundAsset.V23_LAM_I,
        SoundAsset.V23_LAM_U
    ),
    MIM(
        "م",
        "24", "mim",
        SoundAsset.V24_MIM,
        SoundAsset.V24_MIM_A,
        SoundAsset.V24_MIM_I,
        SoundAsset.V24_MIM_U
    ),
    NUN(
        "ن",
        "25", "nun",
        SoundAsset.V25_NUN,
        SoundAsset.V25_NUN_A,
        SoundAsset.V25_NUN_I,
        SoundAsset.V25_NUN_U
    ),
    WAWU(
        "و",
        "26", "wawu",
        SoundAsset.V26_WAWU,
        SoundAsset.V26_WAWU_A,
        SoundAsset.V26_WAWU_I,
        SoundAsset.V26_WAWU_U
    ),
    HA("ه", "27", "ha", SoundAsset.V27_HA, SoundAsset.V27_HA_A, SoundAsset.V27_HA_I, SoundAsset.V27_HA_U),
    YA("ى", "28", "ya", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N01("١", "101","wahid", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N02("٢", "102","itsnan", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N03("٣", "103","tsalatsah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N04("٤", "104","arba'ah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N05("٥", "105","khamsah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N06("٦", "106","sittah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N07("٧", "107","sab'ah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N08("٨", "108","tsamaniyah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N09("٩", "109","tis'ah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
    N10("١٠", "110","'asyrah", SoundAsset.V28_YA, SoundAsset.V28_YA_A, SoundAsset.V28_YA_I, SoundAsset.V28_YA_U),
}
