package com.waynils.exoapplication.remote.audio

import com.waynils.exoapplication.remote.audio.response.AudioApiResponse
import io.reactivex.Single
/*
* Retrofit api service for server data
 */
interface AudioApiService {

    fun getAudioSamples(): Single<AudioApiResponse>
}