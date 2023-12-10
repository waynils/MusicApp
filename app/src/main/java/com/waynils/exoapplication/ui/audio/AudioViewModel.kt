package com.waynils.exoapplication.ui.audio

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.waynils.exoapplication.core.list.BaseListViewModel
import com.waynils.exoapplication.models.AudioModel
import com.waynils.exoapplication.player.MediaController
import com.waynils.exoapplication.providers.audio.AudioProvider
import com.google.android.exoplayer2.SimpleExoPlayer

class AudioViewModel(private val audioProvider: AudioProvider, private val mediaController: MediaController) : BaseListViewModel<AudioModel>(),
        AudioItemActionListener,MediaController.OnMediaControllerCallBack {

    val player = ObservableField<SimpleExoPlayer>()
    val currentAudio = ObservableField<AudioModel>()
    val audioReady = ObservableBoolean(false)
    val haveAudioAddable = ObservableBoolean(false)


    //original audios samples
    override var listLiveData: LiveData<List<AudioModel>> = audioProvider.getAudioSamples()

    init {
        initLives()
    }

    fun addAudioSample(){
        mediaController.addARemovedAudio()
    }

    override fun onDataLoaded() {
       mediaController.setUp(processData,this)
    }

    override fun playAudio(audio: AudioModel) {
        mediaController.playAudio(audio)
    }

    override fun removeAudio(audio: AudioModel) {
        mediaController.removeAudio(audio)
    }

    override fun onPlaylistUpdated(playlist: List<AudioModel>) {
        processData.replace(playlist)
    }

    override fun onCurrentAudioChanged(currentAudio: AudioModel) {
        this.currentAudio.set(currentAudio)
    }

    override fun onAudioAddableUpdated(haveAudioAddable: Boolean) {
        this.haveAudioAddable.set(haveAudioAddable)
    }

    override fun onAudioReady() {
        audioReady.set(true)
    }

    override fun onPlayerReady(player: SimpleExoPlayer) {
       this.player.set(player)
    }

    override fun onCleared() {
        super.onCleared()
        mediaController.release()
        player.set(null)
    }
}