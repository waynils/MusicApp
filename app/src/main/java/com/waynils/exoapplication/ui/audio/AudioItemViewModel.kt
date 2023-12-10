package com.waynils.exoapplication.ui.audio

import com.waynils.exoapplication.models.AudioModel


interface AudioItemActionListener {
    fun playAudio(audio: AudioModel)
    fun removeAudio(audio: AudioModel)
}

class AudioItemViewModel(val audio: AudioModel, private val actionListener: AudioItemActionListener) {

    fun playAudio() {
        actionListener.playAudio(audio)
    }

    fun removeAudio(){
        actionListener.removeAudio(audio)
    }

}