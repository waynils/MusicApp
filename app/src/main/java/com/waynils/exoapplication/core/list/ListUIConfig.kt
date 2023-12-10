package com.waynils.exoapplication.core.list

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.waynils.exoapplication.core.BaseFragment

/**
 * This class represents the graphic configuration for the list
 */
abstract class ListUIConfig<T, VM : BaseListViewModel<T>>(protected val context: Context) {

    constructor(fragment: BaseFragment<*>) : this(fragment.requireContext())

    protected open val canRefresh = false

    open val orientation = OrientationHelper.VERTICAL

    protected open val itemAnimator: RecyclerView.ItemAnimator? = null

    protected open val isReverse = false

    protected open val initialPrefetchCount = 2

    open fun setupRecycler(recyclerView: RecyclerView, viewModel: VM, adapter: RecyclerView.Adapter<*>) {
        recyclerView.layoutManager = createLayoutManager(adapter, viewModel)
        recyclerView.itemAnimator = itemAnimator
        recyclerView.adapter = adapter
    }

    protected open fun createLayoutManager(adapter: RecyclerView.Adapter<*>, viewModel: BaseListViewModel<T>): RecyclerView.LayoutManager {
       return LinearLayoutManager(context, orientation, isReverse).apply {
            initialPrefetchItemCount = initialPrefetchCount
            isSmoothScrollbarEnabled = false
        }
    }

}