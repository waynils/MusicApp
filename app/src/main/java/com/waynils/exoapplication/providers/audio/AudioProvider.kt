package com.waynils.exoapplication.providers.audio

import androidx.lifecycle.LiveData
import com.waynils.exoapplication.models.AudioModel

/*
* A provider for make the connection between remote api and viewmodels
*/
interface AudioProvider {

    fun getAudioSamples(): LiveData<List<AudioModel>>
}