package com.android.pandemic.fighters.base

import androidx.annotation.Keep

@Keep
sealed class DataState<out T : Any> {
    @Keep
    data object Loading : DataState<Nothing>()

    @Keep
    data class Success<out T : Any>(val data: T) : DataState<T>()

    @Keep
    data class Error(val message: String?) : DataState<Nothing>()
}