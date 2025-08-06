package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.LoginInfo
import com.example.listifyjetapp.model.LoginSuccess
import com.example.listifyjetapp.network.ListifyAPI
import javax.inject.Inject

class AuthUserRepository@Inject constructor(private val api: ListifyAPI){
    suspend fun login(loginInfo: LoginInfo): ListifyResult<LoginSuccess> {
        try {
            val response = api.login(loginInfo)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error logging in")
        }
    }
}