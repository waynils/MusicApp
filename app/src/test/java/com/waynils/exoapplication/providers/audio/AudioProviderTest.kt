package com.waynils.exoapplication.providers.audio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.testing.TestLifecycleOwner
import com.waynils.exoapplication.RxImmediateSchedulerRule
import com.waynils.exoapplication.models.AudioModel
import com.waynils.exoapplication.remote.audio.AudioApi
import com.waynils.exoapplication.remote.audio.AudioApiService
import com.waynils.exoapplication.remote.audio.AudioApiServiceImpl
import com.waynils.exoapplication.remote.audio.response.AudioApiResponse
import com.waynils.exoapplication.remote.audio.response.AudioSample
import io.reactivex.Single
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import retrofit2.Response


class AudioProviderTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var testrule = InstantTaskExecutorRule()

    private lateinit var lifecycleOwner: TestLifecycleOwner

    @Mock
    private lateinit var audioApi: AudioApi

    private lateinit var audioApiService: AudioApiService

    private lateinit var audioProvider: AudioProvider

    private val mLiveDataOutput = mutableListOf<AudioModel>()

    @Before
    fun setUp() {
        lifecycleOwner = TestLifecycleOwner(Lifecycle.State.RESUMED)
        MockitoAnnotations.initMocks(this)
        audioApiService = AudioApiServiceImpl(audioApi)
        audioProvider = AudioProviderImpl(audioApiService)
    }

    @Test
    fun `when audio sample are requested, should call audioApiService and return response`() {

        val audioApiResponse = AudioApiResponse(
            listOf(
                AudioSample("sample1", "url1", "title1", "idImage1"),
                AudioSample("sample2", "url2", "title2", "idImage2")
            )
        )

        Mockito.`when`(audioApi.getAudioSamples()).thenReturn(Single.just(Response.success(audioApiResponse)))

        val observer = Observer<List<AudioModel>> {
            mLiveDataOutput.addAll(it)
        }
        val result = audioProvider.getAudioSamples()
        result.observe(lifecycleOwner, observer)

        assertThat(mLiveDataOutput.size, `is`(2))
        assertThat(mLiveDataOutput[0].id, `is`("sample1"))
        assertThat(mLiveDataOutput[1].id, `is`("sample2"))

    }

}