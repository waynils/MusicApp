package com.waynils.exoapplication.ui.audio

import android.os.Bundle
import com.waynils.exoapplication.R
import com.waynils.exoapplication.core.BaseActivity

class AudioActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AudioFragment.newInstance())
                    .commitNow()
        }
    }

}