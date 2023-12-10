package com.waynils.exoapplication.ui.dialog.error

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.waynils.exoapplication.helper.livedata.SingleLiveEvent

class ErrorViewModel : ViewModel() {

    val message = ObservableField<String>()

    val actionEvent = SingleLiveEvent<Boolean>()

    fun init(message: String) {
        this.message.set(message)
    }

    fun performAction(ok: Boolean) {
        actionEvent.value = ok
    }
}