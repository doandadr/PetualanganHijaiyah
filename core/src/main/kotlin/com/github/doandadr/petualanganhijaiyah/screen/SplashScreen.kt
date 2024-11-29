package com.github.doandadr.petualanganhijaiyah.screen

import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.logger


private val LOG = logger<SplashScreen>()

class SplashScreen(val game: Main) : KtxScreen {

    override fun show() {
        val old = System.currentTimeMillis()

        // queue asset loading
        val assetRefs = gdxArrayOf(
            TextureAsset.entries.map {
                game.assets.loadAsync(it.descriptor)
            },
            TextureAtlasAsset.entries.map { game.assets.loadAsync(it.descriptor) },
            SoundAsset.entries.map { game.assets.loadAsync(it.descriptor) }

        ).flatten()

        // once assets are loaded -> change to HomeScreen
        KtxAsync.launch {
            assetRefs.joinAll()
            LOG.debug { "Time for assets to be loaded: ${System.currentTimeMillis() - old} ms" }
            assetsLoaded()
        }

        setupUI()
    }

    private fun setupUI() {
        // TODO Loading Screen UI
        // TODO wait 1 second
    }



    private fun assetsLoaded() {
        game.addScreen(HomeScreen(game))
        game.addScreen(MapScreen(game))
        game.addScreen(LevelScreen(game))
        game.addScreen(PracticeScreen(game))
        game.setScreen<HomeScreen>()
        game.removeScreen<SplashScreen>()
        dispose()
    }
}
