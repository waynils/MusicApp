package com.waynils.exoapplication.player

import android.content.Context
import com.waynils.exoapplication.models.AudioModel
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import io.reactivex.plugins.RxJavaPlugins

class PlayerManagerImpl(private val context: Context) : PlayerManager {

    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var currentAudio: AudioModel? = null
    private var callback: PlayerManager.OnPlayerStateCallback? = null

    override fun setup() {
        if (simpleExoPlayer == null) {
            simpleExoPlayer = SimpleExoPlayer.Builder(context).build()
            simpleExoPlayer?.addListener(eventListener)
        }
    }

    override fun play(audio: AudioModel) {
        val hasAudioChanged = audio.id != currentAudio?.id
        if (hasAudioChanged) {
            val media = MediaItem.fromUri(audio.url)
            simpleExoPlayer?.setMediaItem(media)
            simpleExoPlayer?.prepare()
        }
    }

    override fun getPlayer(): SimpleExoPlayer {
        return simpleExoPlayer!!
    }

    override fun release() {
        callback = null
        simpleExoPlayer?.removeListener(eventListener)
        simpleExoPlayer?.stop()
        simpleExoPlayer?.release()
        simpleExoPlayer = null
    }

    override fun setPlayerCallBack(callback: PlayerManager.OnPlayerStateCallback) {
        this.callback = callback
    }

    private val eventListener = object : Player.EventListener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_ENDED -> callback?.onAudioFinish()

                Player.STATE_BUFFERING -> {
                    //nothing
                }
                Player.STATE_IDLE -> {
                    //nothing
                }
                Player.STATE_READY -> {
                    callback?.onAudioReady()
                }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            RxJavaPlugins.onError(error)
        }
    }

}