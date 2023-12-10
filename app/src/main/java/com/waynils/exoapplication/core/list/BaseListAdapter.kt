package com.waynils.exoapplication.core.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter


/**
 * Default list adapter. This extends [ListAdapter], so all `notify` operations are computed
 * automatically. This handles progress and error cells, and padding footer.
 */
abstract class BaseListAdapter<T, LVM : BaseListViewModel<T>, DB : ViewDataBinding>(
        context: Context,
        private val lifecycleOwner: LifecycleOwner,
        protected val viewModel: LVM
) : ListAdapter<T, BaseBindingViewHolder>(ItemComparator(viewModel)) {

    constructor(fragment: BaseListFragment<T, LVM, DB>) : this(
            fragment.requireContext(),
            fragment.viewLifecycleOwner,
            fragment.listViewModel
    )

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    @LayoutRes
    abstract fun getCellLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val layoutId = getCellLayoutId()

        // Inflates the cell, and its binding if necessary
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                layoutId,
                parent,
                false
        )
        return if (binding == null) {
            BaseBindingViewHolder(layoutInflater.inflate(layoutId, parent, false))
        } else {
            BaseBindingViewHolder(binding, lifecycleOwner)
        }
    }

    /**
     * Creates the item view model associated with the cell
     */
    abstract fun createItemViewModel(item: T): Any

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
            val itemViewModel = createItemViewModel(getItem(position))
            holder.bind(itemViewModel)
    }

    override fun onViewRecycled(holder: BaseBindingViewHolder) = holder.onRecycled()


}