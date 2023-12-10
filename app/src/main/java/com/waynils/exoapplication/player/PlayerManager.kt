package com.waynils.exoapplication.player

import com.waynils.exoapplication.models.AudioModel
import com.google.android.exoplayer2.SimpleExoPlayer

/*
* A simple a manager for the player
*/
interface PlayerManager {

    fun setup()

    fun play(audio: AudioModel)

    fun getPlayer(): SimpleExoPlayer

    fun setPlayerCallBack(callback: OnPlayerStateCallback)

    fun release()

    interface OnPlayerStateCallback {
        fun onAudioReady()
        fun onAudioFinish()
    }

}