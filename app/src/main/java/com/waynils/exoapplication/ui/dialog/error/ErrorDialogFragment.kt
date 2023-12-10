package com.waynils.exoapplication.ui.dialog.error

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.waynils.exoapplication.databinding.DialogErrorBinding

/*
 * A dialog for display when an error occured
 */
open class ErrorDialogFragment : AppCompatDialogFragment() {

    private val viewModel by viewModels<ErrorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel.apply {
            actionEvent.observe(this@ErrorDialogFragment, Observer {
                dismissAllowingStateLoss()
            })
            arguments?.run {
                isCancelable = getBoolean(CANCELABLE, true)
                init(getString(MESSAGE, ""))
            }
        }

    }

    fun show(activity: AppCompatActivity, tag: String? = null) {
        super.show(activity.supportFragmentManager, tag)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogErrorBinding
            .inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                vm = viewModel
            }
            .root
    }

    companion object {
        private const val MESSAGE = "message"
        private const val CANCELABLE = "cancelable"

        fun newInstance(message: String, cancelable: Boolean = true): ErrorDialogFragment {

            return ErrorDialogFragment().apply {
                arguments = bundleOf(
                    MESSAGE to message,
                    CANCELABLE to cancelable
                )
            }
        }
    }

}