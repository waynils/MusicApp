package com.waynils.exoapplication

import android.app.Application
import com.waynils.exoapplication.player.MediaControllerImpl
import com.waynils.exoapplication.player.PlayerManagerImpl
import com.waynils.exoapplication.remote.RetrofitApi
import com.waynils.exoapplication.remote.audio.AudioApiService
import com.waynils.exoapplication.remote.audio.AudioApiServiceImpl
import com.waynils.exoapplication.providers.audio.AudioProviderImpl

/*
* This for manual dependency injection see on https://developer.android.com/training/dependency-injection/manual
 */
class AppContainer(application: Application) {

    private val audioApiService: AudioApiService = AudioApiServiceImpl(RetrofitApi.audioApiService)
    val audioRepository = AudioProviderImpl(audioApiService)
    private val playerManager = PlayerManagerImpl(application)
    val mediaController = MediaControllerImpl(playerManager)
}