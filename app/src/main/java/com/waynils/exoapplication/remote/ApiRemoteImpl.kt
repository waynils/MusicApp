package com.waynils.exoapplication.remote

import android.util.Log
import com.waynils.exoapplication.Constants
import com.waynils.exoapplication.remote.exceptions.BadRequestException
import com.waynils.exoapplication.remote.exceptions.ForbiddenException
import com.waynils.exoapplication.remote.exceptions.NotFoundException
import com.waynils.exoapplication.remote.exceptions.ServerErrorException
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.Response

/*
* class to handle http error
 */
abstract class ApiRemoteImpl {


    private fun handleStatusCodeToSendEmitter(emitter: SingleEmitter<*>, statusCode: Int) {
        when (statusCode) {
            Constants.HTTPStatus.NOT_FOUND.code -> onNotFoundRequest(emitter)
            Constants.HTTPStatus.BAD_REQUEST.code -> onBadRequest(emitter)
            Constants.HTTPStatus.FORBIDDEN.code -> onForbiddenRequest(emitter)
            Constants.HTTPStatus.INTERNAL_SERVER_ERROR.code -> onServerError(emitter)
        }
    }

    private fun onNotFoundRequest(emitter: SingleEmitter<*>) {
        emitter.onError(NotFoundException())
    }

    private fun onBadRequest(emitter: SingleEmitter<*>) {
        emitter.onError(BadRequestException())
    }

    private fun onForbiddenRequest(emitter: SingleEmitter<*>) {
        emitter.onError(ForbiddenException())
    }

    private fun onServerError(emitter: SingleEmitter<*>) {
        emitter.onError(ServerErrorException())
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any, V : Any> Single<T>.subscribeResponse(emitter: SingleEmitter<V>): Disposable {
        return this.subscribeBy(
            onSuccess = { response ->
                if ((response as Response<*>).isSuccessful) {
                    Log.d("Response",response.body().toString())
                    response.body()?.let { data ->
                        emitter.onSuccess(data as V)
                    }
                } else {
                    handleStatusCodeToSendEmitter(emitter, response.code())
                }
            }
        )

    }

}









