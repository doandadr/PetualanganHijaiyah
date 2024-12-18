@file:JvmName("Lwjgl3Launcher")

package com.github.doandadr.petualanganhijaiyah.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.lwjgl3.ml.DesktopRecognition
import org.tensorflow.SavedModelBundle

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
        return

    val mlModel: SavedModelBundle by lazy {
//        SavedModelBundle.load("assets/ml", "serve")
        SavedModelBundle.load("lwjgl3/src/main/ml", "serve")
    }

    Lwjgl3Application(Main(DesktopRecognition(mlModel)), Lwjgl3ApplicationConfiguration().apply {
        setTitle("PetualanganHijaiyah")
        // figma (360, 706)
        // phone (432, 960)
        // real phone (1080, 1920)
        setWindowedMode(432, 960)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
