package com.example.listifyjetapp.utils

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.network.ListifyAPI
import retrofit2.HttpException
import androidx.datastore.core.IOException


suspend fun<T>  ListifyAPI.safeApiCall(
    call: suspend ListifyAPI.() -> T,
    defaultErrorMessage: String
): ListifyResult<T> {
    return try {
        val response = call()
        ListifyResult.Success(data = response)
    } catch(e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody) ?: defaultErrorMessage
        ListifyResult.Failure(errorMessage)
    } catch (e: IOException) {
        // Handle network errors
        ListifyResult.Failure("Network error: ${e.message}")
    } catch (e:Exception) {
        val errorBody = e.message
        val errorMessage = parseErrorMessage(errorBody) ?: defaultErrorMessage
        ListifyResult.Failure(errorMessage)
    }
}