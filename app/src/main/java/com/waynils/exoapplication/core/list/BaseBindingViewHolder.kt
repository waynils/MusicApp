package com.waynils.exoapplication.core.list

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.waynils.exoapplication.BR

/**
 * View holder for data binding.
 */
open class BaseBindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var dataBinding: ViewDataBinding? = null

    private var lifecycleOwner: LifecycleOwner? = null

    /**
     * Constructor for data binding
     */
    constructor(
        dataBinding: ViewDataBinding,
        lifecycleOwner: LifecycleOwner? = null
    ) : this(dataBinding.root) {
        this.dataBinding = dataBinding
        this.lifecycleOwner = lifecycleOwner
    }

    /**
     * Uses databinding to bind the view model and the config to the view
     */
    fun bind(viewModel: Any?) {
        dataBinding?.setVariable(BR.vm, viewModel)
        if (lifecycleOwner == null) {
            dataBinding?.executePendingBindings()
        } else {
            dataBinding?.lifecycleOwner = lifecycleOwner
        }


    }

    open fun onRecycled() {
        dataBinding?.lifecycleOwner = null
    }
}