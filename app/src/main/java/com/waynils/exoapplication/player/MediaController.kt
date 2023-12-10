package com.waynils.exoapplication.player

import com.waynils.exoapplication.models.AudioModel
import com.google.android.exoplayer2.SimpleExoPlayer


/**
 * An controller for manage playlist and control the player
 * use the callback for update your values in viewmodels
 */
interface MediaController {

    fun setUp(
        originalPlaylist: List<AudioModel>,
        mediaControllerCallBack: OnMediaControllerCallBack
    )

    fun playAudio(audio: AudioModel)

    fun addARemovedAudio()

    fun removeAudio(audio: AudioModel)

    fun release()


    interface OnMediaControllerCallBack {
        fun onPlaylistUpdated(playlist: List<AudioModel>)
        fun onCurrentAudioChanged(currentAudio: AudioModel)
        fun onAudioReady()
        fun onPlayerReady(player: SimpleExoPlayer)
        fun onAudioAddableUpdated(haveAudioAddable: Boolean)
    }

}