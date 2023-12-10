package com.waynils.exoapplication.core.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.waynils.exoapplication.core.BaseActivity
import com.waynils.exoapplication.core.BaseFragment
import com.waynils.exoapplication.BR

abstract class BaseListFragment<T, LVM : BaseListViewModel<T>, DB : ViewDataBinding> : BaseFragment<DB>() {

    private val uiConfig by lazy { createUIConfig() }

    private lateinit var adapter: BaseListAdapter<T, LVM, DB>

    abstract val listViewModel: LVM

    protected abstract val recyclerView: RecyclerView?

    override fun setupDataBinding() {
        dataBinding?.setVariable(BR.lvm, listViewModel)
        recyclerView?.apply {
            uiConfig.setupRecycler(this, listViewModel, this@BaseListFragment.adapter)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireActivity() as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!this::adapter.isInitialized) {
            adapter = createAdapter()
        }

        listViewModel.processData.observe(viewLifecycleOwner, { data ->
            adapter.submitList(data)
        })

        listViewModel.liveIsLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                activity.showProgressDialog(true)
            } else
                activity.hideProgressDialog()
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected abstract fun createUIConfig(): ListUIConfig<T, LVM>

    protected abstract fun createAdapter(): BaseListAdapter<T, LVM, DB>
}