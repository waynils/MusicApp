package com.waynils.exoapplication.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * BaseFragment for manage databinding , just extend for simplify your life
 *
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val contentId: Int

    protected lateinit var activity: BaseActivity

    protected var dataBinding: DB? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireActivity() as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (dataBinding == null) {
            dataBinding = try {
                DataBindingUtil
                    .bind<DB>(inflater.inflate(contentId, container, false))
                    ?.apply {
                        lifecycleOwner = this@BaseFragment
                    }
            } catch (e: IllegalArgumentException) {
                null
            }
            setupDataBinding()
        }

        return dataBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (dataBinding?.root?.parent != null)
            (dataBinding?.root?.parent as ViewGroup).removeView(dataBinding?.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    abstract fun setupDataBinding()

}