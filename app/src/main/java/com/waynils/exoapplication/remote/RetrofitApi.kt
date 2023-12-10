package com.waynils.exoapplication.remote

import com.waynils.exoapplication.Constants
import com.waynils.exoapplication.remote.audio.AudioApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApi {

    private const val BASE_URL = Constants.PROD_URL

    val audioApiService: AudioApi by lazy {
        buildRetrofit().create(AudioApi::class.java)
    }

    private fun buildRetrofit(): Retrofit {
         val moshi = Moshi.Builder()
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(buildOkHttpClient())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val okhttpClientBuilder = OkHttpClient.Builder()
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        return okhttpClientBuilder.build()
    }

}