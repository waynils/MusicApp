package com.waynils.exoapplication.core

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.waynils.exoapplication.R
import com.waynils.exoapplication.remote.exceptions.*
import com.waynils.exoapplication.ui.dialog.ProgressDialogFragment
import com.waynils.exoapplication.ui.dialog.error.ErrorDialogFragment
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins
import java.net.UnknownHostException

/**
 *
 * This BaseActivity handles error and manage progress display .
 */
abstract class BaseActivity() : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialogFragment.newInstance()
        initializeRxJavaErrorHandler()
    }

    override fun onResume() {
        super.onResume()
        if (RxJavaPlugins.getErrorHandler() == null) {
            initializeRxJavaErrorHandler()
        }
    }

    override fun onPause() {
        super.onPause()
        RxJavaPlugins.setErrorHandler(null)
    }

    private fun initializeRxJavaErrorHandler() {

        RxJavaPlugins.setErrorHandler { e ->
            e.printStackTrace()

            var throwable = e

            if (throwable is OnErrorNotImplementedException) {
                throwable = throwable.cause
            }

            @StringRes val stringRes: Int = when (throwable) {
                UnknownHostException() -> R.string.no_network
                ForbiddenException() -> R.string.generic_error
                NotFoundException() -> R.string.generic_error
                BadRequestException() -> R.string.generic_error
                ServerErrorException() -> R.string.generic_error
                else -> R.string.generic_error
            }

            ErrorDialogFragment.newInstance(getString(stringRes))
                .show(this, null)
        }
    }

    fun showProgressDialog(withDim: Boolean = false) {
        if (!progressDialog.isAdded) {
            if (withDim) {
                progressDialog.showWithDim(this, "ProgressDialog")
            } else {
                progressDialog.show(this, "ProgressDialog")
            }
        }
    }

    fun hideProgressDialog() {
        if (progressDialog.isAdded) {
            progressDialog.dismiss()
        }
    }

}