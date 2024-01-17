package com.android.pandemic.fighters.base

import androidx.annotation.Keep

@Keep
sealed class ResponseState<out T : Any> {
    @Keep
    data object Loading : ResponseState<Nothing>()

    @Keep
    data class Success<out T : Any>(val data: T) : ResponseState<T>()

    @Keep
    data class Error(val message: String?) : ResponseState<Nothing>()
}