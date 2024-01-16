package com.android.pandemic.fighters.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <T : Any> handleApiResponse(
    crossinline responseFunction: suspend () -> T
): DataState<T> = try {
    val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
    if ((response as? Response<*>)?.isSuccessful == false) DataState.Error(response.errorBody()?.string())
    else DataState.Success(response)
} catch (e: Exception) {
    e.printStackTrace()
    DataState.Error(e.message)
}