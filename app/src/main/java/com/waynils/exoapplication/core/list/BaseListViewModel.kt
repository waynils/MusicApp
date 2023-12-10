package com.waynils.exoapplication.core.list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.waynils.exoapplication.helper.extension.changeValue
import com.waynils.exoapplication.helper.livedata.MutableLiveList
import com.waynils.exoapplication.models.Indexed


/**
 * Default [DiffUtil.ItemCallback], delegating the comparison to the [BaseListViewModel]
 */
internal class ItemComparator<T>(private val viewModel: BaseListViewModel<T>) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return viewModel.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return viewModel.areContentsTheSame(oldItem, newItem)
    }
}

@Suppress("UNCHECKED_CAST")
abstract class BaseListViewModel<T> : ViewModel() {

    val noData = ObservableBoolean()

    val liveIsLoading = MediatorLiveData<Boolean>()

    protected abstract val listLiveData: LiveData<List<T>>

    val processData = MutableLiveList<T>()

    fun initLives() {
        liveIsLoading.changeValue(true)
        liveIsLoading.apply {
            addSource(listLiveData) { items ->
                noData.set(items.isEmpty())
                if (processData.isEmpty())
                    processData.addAll(items)
                else
                    processData.replace(items)
                onDataLoaded()
                value = false
            }
        }
    }


    fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Indexed && newItem is Indexed) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }
    }

    fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    protected open fun onDataLoaded() {}

}



