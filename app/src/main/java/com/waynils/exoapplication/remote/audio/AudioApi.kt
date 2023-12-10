package com.waynils.exoapplication.remote.audio

import com.waynils.exoapplication.remote.audio.response.AudioApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface AudioApi {

    @GET("audios.php")
    fun getAudioSamples(): Single<Response<AudioApiResponse>>
}