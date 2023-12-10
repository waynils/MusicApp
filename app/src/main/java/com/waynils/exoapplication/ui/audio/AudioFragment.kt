package com.waynils.exoapplication.ui.audio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.waynils.exoapplication.MusicApp
import com.waynils.exoapplication.R
import com.waynils.exoapplication.core.list.BaseListAdapter
import com.waynils.exoapplication.core.list.BaseListFragment
import com.waynils.exoapplication.core.list.ListUIConfig
import com.waynils.exoapplication.databinding.AudioFragmentBinding
import com.waynils.exoapplication.lifecycle.MainViewModelFactory
import com.waynils.exoapplication.models.AudioModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator

class AudioFragment : BaseListFragment<AudioModel, AudioViewModel, AudioFragmentBinding>() {

    override val contentId: Int = R.layout.audio_fragment

    override val listViewModel: AudioViewModel by viewModels {
        (requireActivity().application as MusicApp).appContainer.let { appContainer ->
            MainViewModelFactory(appContainer.audioRepository, appContainer.mediaController)
        }
    }

    override val recyclerView: RecyclerView? get() = dataBinding?.rvAudio


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding?.main?.setOnClickListener { dataBinding?.playerControlView?.show() }

    }


    override fun createUIConfig(): ListUIConfig<AudioModel, AudioViewModel> =
            object : ListUIConfig<AudioModel, AudioViewModel>(this) {
                override val itemAnimator: RecyclerView.ItemAnimator = SlideInLeftAnimator()
            }

    override fun createAdapter(): BaseListAdapter<AudioModel, AudioViewModel, AudioFragmentBinding> =
            object : BaseListAdapter<AudioModel, AudioViewModel, AudioFragmentBinding>(this) {
                override fun getCellLayoutId(): Int = R.layout.item_audio

                override fun createItemViewModel(item: AudioModel): Any {
                    return AudioItemViewModel(item, viewModel)
                }
            }

    companion object {
        fun newInstance() = AudioFragment()
    }
}
