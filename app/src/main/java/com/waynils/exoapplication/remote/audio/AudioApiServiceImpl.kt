package com.waynils.exoapplication.remote.audio

import com.waynils.exoapplication.remote.ApiRemoteImpl
import com.waynils.exoapplication.remote.audio.response.AudioApiResponse
import io.reactivex.Single

class AudioApiServiceImpl(private val audioApi: AudioApi) : AudioApiService,
    ApiRemoteImpl() {

    override fun getAudioSamples(): Single<AudioApiResponse> = Single.create { emitter ->
        audioApi.getAudioSamples()
            .subscribeResponse(emitter)
    }

}