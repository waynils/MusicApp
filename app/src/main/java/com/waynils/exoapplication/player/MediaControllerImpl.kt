package com.waynils.exoapplication.player

import android.util.Log
import com.waynils.exoapplication.helper.extension.removeAny
import com.waynils.exoapplication.models.AudioModel
import java.lang.Exception

class MediaControllerImpl(private val playerManager: PlayerManager) : MediaController,
    PlayerManager.OnPlayerStateCallback {

    private val audioSamplesRemoved = mutableListOf<AudioModel>()
    private val playList = mutableListOf<AudioModel>()
    private val indexOfFirstAudio = 0
    private var callback: MediaController.OnMediaControllerCallBack? = null
    private var currentAudio: AudioModel? = null

    override fun setUp(
        originalPlaylist: List<AudioModel>,
        mediaControllerCallBack: MediaController.OnMediaControllerCallBack
    ) {
        playList.addAll(originalPlaylist)
        playerManager.setup()
        playerManager.setPlayerCallBack(this)
        this.callback = mediaControllerCallBack
        try {
            callback?.onPlayerReady(playerManager.getPlayer())
        } catch (e: Exception) {
            Log.e("MediaController", "player need to be initialize")
        }
    }

    override fun playAudio(audio: AudioModel) {
        playerManager.play(audio)
        removeAudio(audio)
        currentAudio = audio
        callback?.onCurrentAudioChanged(audio)
    }

    override fun addARemovedAudio() {
        val filteredList = audioSamplesRemoved.filterNot { it.id == currentAudio?.id }
        playList.add(filteredList.last()).also {
            audioSamplesRemoved.remove(filteredList.last())
        }
        callback?.onAudioAddableUpdated(haveAudioAddable())
        callback?.onPlaylistUpdated(playList)
    }

    override fun removeAudio(audio: AudioModel) {
        audioSamplesRemoved.add(audio)
        playList.removeAny { it.id == audio.id }
        callback?.onPlaylistUpdated(playList)
        callback?.onAudioAddableUpdated(haveAudioAddable())
    }

    override fun release() {
        callback = null
        audioSamplesRemoved.clear()
        playList.clear()
        playerManager.release()
    }

    override fun onAudioReady() {
        callback?.onAudioReady()
    }

    override fun onAudioFinish() {
        if (playList.size > 0)
            playAudio(playList[indexOfFirstAudio])
    }

    private fun haveAudioAddable(): Boolean = audioSamplesRemoved.size > 1

}