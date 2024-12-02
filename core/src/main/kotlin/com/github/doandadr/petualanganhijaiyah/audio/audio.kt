package com.github.doandadr.petualanganhijaiyah.audio

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Pool
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import kotlinx.coroutines.launch
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.log.logger
import java.util.*
import kotlin.math.max

private val log = logger<AudioService>()
private const val MAX_SOUND_INSTANCES = 8

interface AudioService {
    var enabled: Boolean
    var soundVolume: Float
    var musicVolume: Float
    fun play(soundAsset: SoundAsset, volume: Float = soundAsset.volume) = Unit
    fun play(musicAsset: MusicAsset, volume: Float = musicAsset.volume, loop: Boolean = true) = Unit
    fun pause() = Unit
    fun resume() = Unit
    fun stop(clearSounds: Boolean = true) = Unit
    fun update() = Unit
}

private class SoundRequest : Pool.Poolable {
    lateinit var soundAsset: SoundAsset
    var volume = 1f

    override fun reset() {
        volume = 1f
    }
}

private class SoundRequestPool :
    Pool<SoundRequest>() {
    override fun newObject(): SoundRequest = SoundRequest()
}

class DefaultAudioService(private val assets: AssetStorage) : AudioService {
    private val soundCache = EnumMap<SoundAsset, Sound>(SoundAsset::class.java)
    private val soundRequestPool = SoundRequestPool()
    private val soundRequests = EnumMap<SoundAsset, SoundRequest>(SoundAsset::class.java)
    private var currentMusic: Music? = null
    private var currentMusicAsset = MusicAsset.HOME
    override var enabled = true
        set(value) {
            when (value) {
                true -> currentMusic?.play()
                false -> currentMusic?.pause()
            }
            field = value
        }
    override var soundVolume = 1f
        set(value) {
            field = MathUtils.clamp(value, 0f, 1f)
        }
    override var musicVolume = 1f
        set(value) {
            field = MathUtils.clamp(value, 0f, 1f)
            currentMusic?.volume = field * currentMusicAsset.volume
        }

    override fun play(soundAsset: SoundAsset, volume: Float) {
        if (!enabled) return

        when {
            soundAsset in soundRequests -> {
                // same sound request is done in one frame multiple times
                // play sound only once with the highest volume of both request
                log.debug { "Duplicated sound request for sound $soundAsset" }
                soundRequests[soundAsset]?.let { request ->
                    request.volume = max(request.volume, volume)
                }
            }

            soundRequests.size >= MAX_SOUND_INSTANCES -> {
                log.debug { "Maximum sound request reached" }
            }

            else -> {
                if (soundAsset.descriptor !in assets) {
                    log.error { "Trying to play a sound which is not loaded: $soundAsset" }
                    return
                } else if (soundAsset !in soundCache) {
                    log.debug { "Adding sound $soundAsset to sound cache" }
                    soundCache[soundAsset] = assets[soundAsset.descriptor]
                }

                // get request instance from pool and add it to the queue
                soundRequests[soundAsset] = soundRequestPool.obtain().apply {
                    this.soundAsset = soundAsset
                    this.volume = volume
                }
            }
        }
    }

    override fun play(musicAsset: MusicAsset, volume: Float, loop: Boolean) {
        if (currentMusic != null) {
            currentMusic?.stop()
            KtxAsync.launch {
                assets.unload(currentMusicAsset.descriptor)
            }
        }

        val musicDeferred = assets.loadAsync(musicAsset.descriptor)
        KtxAsync.launch {
            musicDeferred.join()
            if (assets.isLoaded(musicAsset.descriptor)) {
                currentMusicAsset = musicAsset
                if (!enabled) return@launch
                currentMusic = assets[musicAsset.descriptor].apply {
                    this.volume = musicVolume * volume
                    this.isLooping = loop
                    play()
                }
            }
        }
    }

    override fun pause() {
        currentMusic?.pause()
    }

    override fun resume() {
        if (!enabled) return

        currentMusic?.play()
    }

    override fun stop(clearSounds: Boolean) {
        currentMusic?.stop()
        if (clearSounds) {
            soundRequests.clear()
        }
    }

    override fun update() {
        if (soundRequests.isNotEmpty()) {
            log.debug { "Playing ${soundRequests.size} sound(s)" }
            soundRequests.values.forEach { request ->
                soundCache[request.soundAsset]?.play(soundVolume * request.volume)
                soundRequestPool.free(request)
            }
            soundRequests.clear()
        }
    }
}
