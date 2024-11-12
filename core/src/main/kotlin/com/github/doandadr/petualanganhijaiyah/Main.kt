package com.github.doandadr.petualanganhijaiyah

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.doandadr.petualanganhijaiyah.screen.FirstScreen
import com.ray3k.stripe.FreeTypeSkin
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import ktx.freetype.registerFreeTypeFontLoaders
import ktx.log.logger
import ktx.scene2d.Scene2DSkin

const val UNIT_SCALE = 1/16f
const val V_WIDTH_PIXELS = 720
const val V_HEIGHT_PIXELS = 1412
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
        val skin = FreeTypeSkin(Gdx.files.internal("skin/skin.json"))
        Scene2DSkin.defaultSkin = skin
        result
    }

    val assetManager: AssetManager by lazy { initiateAssetManager() }
    fun initiateAssetManager(): AssetManager {
        val assetManager = AssetManager()
        assetManager.registerFreeTypeFontLoaders()
        return assetManager
    }

    // TODO gameEventManager
    // TODO audioService
    // TODO preferences

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
        stage.disposeSafely()
    }
}
