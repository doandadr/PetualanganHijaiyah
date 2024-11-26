package com.github.doandadr.petualanganhijaiyah.audio

import com.badlogic.gdx.Audio
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Pool
import com.github.doandadr.petualanganhijaiyah.asset.MusicAsset
import com.github.doandadr.petualanganhijaiyah.asset.SoundAsset
import kotlinx.coroutines.launch
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.log.logger
import java.util.*
import kotlin.math.max

private val LOG = logger<AudioService>()
private const val MAX_SOUND_INSTANCES = 8

interface AudioService {
    var enabled: Boolean
    fun play(soundAsset: SoundAsset, volume: Float = 1f) = Unit
    fun play(musicAsset: MusicAsset, volume: Float = 1f, loop: Boolean = true) = Unit
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
    override var enabled = true
        set(value) {
            when (value) {
                true -> currentMusic?.play()
                false -> currentMusic?.pause()
            }
            field = value
        }
    private val soundCache = EnumMap<SoundAsset, Sound>(SoundAsset::class.java)
    private val soundRequestPool = SoundRequestPool()
    private val soundRequests = EnumMap<SoundAsset, SoundRequest>(SoundAsset::class.java)
    private var currentMusic: Music? = null
    private var currentMusicAsset = MusicAsset.HOME

    override fun play(soundAsset: SoundAsset, volume: Float) {
        when {
            soundAsset in soundRequests -> {
                // same sound request is done in one frame multiple times
                // play sound only once with the highest volume of both request
                soundRequests[soundAsset]?.let { request ->
                    request.volume = max(request.volume, volume)
                }
            }

            soundRequests.size >= MAX_SOUND_INSTANCES -> {
                LOG.debug { "Maximum sound request reached" }
                return
            }

            else -> {
                if (soundAsset.descriptor !in assets) {
                    LOG.error { "Trying to play a sound which is not loaded: $soundAsset" }
                    return
                } else if (soundAsset !in soundCache) {
                    soundCache[soundAsset] = assets[soundAsset.descriptor]
                }

                // play the sound
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
                    this.volume = volume
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
            soundRequests.values.forEach { request ->
                soundCache[request.soundAsset]?.play(request.volume)
                soundRequestPool.free(request)
            }
            soundRequests.clear()
        }
    }
}
