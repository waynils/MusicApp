package com.waynils.exoapplication.remote.audio.response

import com.squareup.moshi.Json

data class AudioApiResponse(

        @field:Json(name = "list_audio")
        val audioSamples: List<AudioSample>
)

data class AudioSample(

        @field:Json(name = "id")
        val id: String,

        @field:Json(name = "url")
        val url: String,

        @field:Json(name = "title")
        val title: String,

        @field:Json(name = "id_image")
        val idImage: String

)
