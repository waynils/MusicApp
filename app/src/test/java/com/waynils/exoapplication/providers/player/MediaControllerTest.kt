package com.waynils.exoapplication.providers.player

import com.waynils.exoapplication.models.AudioModel
import com.waynils.exoapplication.player.MediaController
import com.waynils.exoapplication.player.MediaControllerImpl
import com.waynils.exoapplication.player.PlayerManager
import com.google.android.exoplayer2.SimpleExoPlayer
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

class MediaControllerTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var playerManager: PlayerManager

    @Mock
    private lateinit var callback: MediaController.OnMediaControllerCallBack

    @Mock
    private lateinit var player: SimpleExoPlayer

    private lateinit var mediaController: MediaController

    val playlist = mutableListOf<AudioModel>(
        AudioModel("sample1", "url1", "titre1", "idImage1"),
        AudioModel("sample2", "url2", "titre2", "idImage2"),
        AudioModel("sample3", "url3", "titre3", "idImage3")
    )


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mediaController = MediaControllerImpl(playerManager)

        Mockito.`when`(playerManager.getPlayer()).thenReturn(player)

        mediaController.setUp(playlist, callback)
    }

    @Test
    fun `when mediaController is setup callback onPlayerReady is call`() {

        verify(callback, times(1)).onPlayerReady(player)
    }

    @Test
    fun `when mediaController is playing playlist is update `() {
        mediaController.playAudio(playlist[0])
        verify(callback, times(1)).onPlaylistUpdated(playlist.filterNot { it.id == playlist[0].id })
    }

    @Test
    fun `when mediaController remove an audio , playlist is update if list removed has more one item`() {
        mediaController.removeAudio(playlist[0])
        verify(callback, times(1)).onPlaylistUpdated(playlist.filterNot { it.id == playlist[0].id })
    }

    @Test
    fun `when mediaController add a removed audio in end queue , playlist is update if list removed has more one item`() {
        mediaController.removeAudio(playlist[0])
        mediaController.removeAudio(playlist[1])
        mediaController.addARemovedAudio()
        verify(callback, times(3)).onPlaylistUpdated(listOf(playlist[2],playlist[1]))
    }

    @Test
    fun `when mediaController playAudio currentAudioChange`() {
       mediaController.playAudio(playlist[0])
        verify(callback, times(1)).onCurrentAudioChanged(playlist[0])
    }

    @Test
    fun `when mediaController remove only one audio haveAudioAddable is false `() {
        mediaController.removeAudio(playlist[0])
        verify(callback, times(1)).onAudioAddableUpdated(false)
    }


    @Test
    fun `when mediaController add a removed audio and stay one or more removed audio haveAudioAddable is true `() {
        mediaController.removeAudio(playlist[0])
        mediaController.removeAudio(playlist[1])
        mediaController.removeAudio(playlist[2])
        mediaController.addARemovedAudio()
        verify(callback, times(3)).onAudioAddableUpdated(true)
    }

}