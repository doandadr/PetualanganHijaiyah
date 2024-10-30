package com.github.doandadr.petualanganhijaiyah

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import com.github.doandadr.petualanganhijaiyah.screen.FirstScreen
import com.github.doandadr.petualanganhijaiyah.screen.Screen
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.graphics.use
import ktx.log.logger

const val UNIT_SCALE = 1/16f
const val V_WIDTH_PIXELS = 360
const val V_HEIGHT_PIXELS = 640
const val V_WIDTH = 9
const val V_HEIGHT = 16
private val LOG = logger<Main>()

class Main : KtxGame<KtxScreen>() {

    val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())
    val batch: Batch by lazy{SpriteBatch()}
    val assets: AssetStorage by lazy {
        KtxAsync.initiate()
        AssetStorage()
    }
    val stage: Stage by lazy {
        val result = Stage(uiViewport, batch)
        Gdx.input.inputProcessor = result
        result
    }

    // TODO gameEventManager
    // TODO audioService
    // TODO preferences
//    fun initiateAssetManager(): AssetManager {
//        val assetManager = AssetManager()
//        assetManager.registerFreeTypeFontLoaders()
//        return assetManager
//    }



    override fun create() {
        KtxAsync.initiate()
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Create game instance" }

        addScreen(FirstScreen(this))
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        super.dispose()
        LOG.debug { "Sprites in batch: ${(batch as SpriteBatch).maxSpritesInBatch}" }
        batch.disposeSafely()
        assets.disposeSafely()
    }
}
