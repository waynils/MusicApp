package com.waynils.exoapplication.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waynils.exoapplication.player.MediaController
import com.waynils.exoapplication.providers.audio.AudioProvider
/*
* a custom ViewmodelFactory for pass parameter in mainviewmodel constructor
 */
internal class MainViewModelFactory(private val audioProvider: AudioProvider, private val mediaController: MediaController) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AudioProvider::class.java, MediaController::class.java).newInstance(audioProvider, mediaController)
    }
}