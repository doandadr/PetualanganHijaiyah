package com.github.doandadr.petualanganhijaiyah.screen

import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.logger

private val LOG = logger<FirstScreen>()

class FirstScreen(game: Main) : Screen(game) {

    override fun show() {
        val old = System.currentTimeMillis()

        // queue asset loading
        val assetRefs = gdxArrayOf(
            TextureAsset.entries.map {
                assets.loadAsync(it.descriptor)
            },
            TextureAtlasAsset.entries.map { assets.loadAsync(it.descriptor) }
        ).flatten()

        // once assets ar loaded -> change to HomeScreen
        KtxAsync.launch {
            assetRefs.joinAll()
            LOG.debug { "Time for assets to be loaded: ${System.currentTimeMillis() - old} ms" }
            assetsLoaded()
        }

        // TODO Loading Screen UI
    }

    private fun assetsLoaded() {
        game.addScreen(HomeScreen(game))
        game.setScreen<HomeScreen>()
        game.removeScreen<FirstScreen>()
        dispose()
    }
}
