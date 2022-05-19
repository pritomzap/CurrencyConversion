package com.currencyconversion.app.data.repositories

import android.content.Context
import com.currencyconversion.app.R
import com.currencyconversion.app.service.network.NetworkResult
import retrofit2.Response


abstract class BaseRepository(private val context:Context){

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        val response = apiCall()
        try {
            return if (response.isSuccessful && response.code() == HTTP_STATUS.HTTP_OK.code) {
                val body = response.body()
                NetworkResult.Success(body,null)
            }else if (response.code() == HTTP_STATUS.HTTP_UNAUTHENTICATE.code){
                error(response.code(), response.message())
            }else
                error(response.code(), response.message())
        } catch (e: Exception) {
            return error(0, e.message?:context.getString(R.string.app_common_api_error))
        }
    }

    private fun <T> error(code:Int,errorMessage: String,data: T?=null): NetworkResult<T> = NetworkResult.Error(
        errorMessage,
        data
    )

    enum class HTTP_STATUS(val code:Int){
        HTTP_OK(200),
        HTTP_UNAUTHENTICATE(401)
    }
}