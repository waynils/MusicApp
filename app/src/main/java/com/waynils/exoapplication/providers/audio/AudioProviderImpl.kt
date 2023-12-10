package com.waynils.exoapplication.providers.audio

import androidx.lifecycle.LiveData
import com.waynils.exoapplication.helper.extension.toLiveData
import com.waynils.exoapplication.remote.audio.AudioApiService
import com.waynils.exoapplication.models.AudioModel
import com.waynils.exoapplication.remote.audio.response.AudioSample
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class AudioProviderImpl(private val audioApiService: AudioApiService) : AudioProvider {

    //transform rx to livedata
    override fun getAudioSamples(): LiveData<List<AudioModel>> {
        return audioApiService.getAudioSamples().map { response ->
            response.audioSamples.map { item ->
                item.toAudioModel()
            }
        }.doOnError {
            RxJavaPlugins.onError(it)
        }
                .onErrorReturnItem(listOf())
                .subscribeOn(Schedulers.io()).toLiveData()

    }


    private fun AudioSample.toAudioModel(): AudioModel {
        return AudioModel(this.id, this.url, this.title, this.idImage)
    }

}