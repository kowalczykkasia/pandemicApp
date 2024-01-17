package com.android.pandemic.fighters.utils

import com.android.pandemic.fighters.base.ResponseState

fun <T : Any> ResponseState<T>.handleResponseState(
    loadingFun: () -> Unit? = {},
    successFun: (T) -> Unit? = {},
    errorFun: (String?) -> Unit? = {}
) {
    when (this) {
        is ResponseState.Success -> successFun.invoke(this.data)
        is ResponseState.Error -> errorFun.invoke(this.message)
        is ResponseState.Loading -> loadingFun.invoke()
    }
}