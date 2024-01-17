package com.android.pandemic.fighters.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <T : Any> handleApiResponse(
    crossinline responseFunction: suspend () -> T
): ResponseState<T> = try {
    val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
    if ((response as? Response<*>)?.isSuccessful == false) ResponseState.Error(response.errorBody()?.string())
    else ResponseState.Success(response)
} catch (e: Exception) {
    e.printStackTrace()
    ResponseState.Error(e.message)
}