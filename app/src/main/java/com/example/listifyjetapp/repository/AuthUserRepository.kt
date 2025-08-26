package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.LoginInfo
import com.example.listifyjetapp.model.LoginSuccess
import com.example.listifyjetapp.network.ListifyAPI
import com.example.listifyjetapp.utils.safeApiCall
import javax.inject.Inject

class AuthUserRepository @Inject constructor(private val api: ListifyAPI){
    suspend fun login(loginInfo: LoginInfo): ListifyResult<LoginSuccess> {
        return api.safeApiCall(
            call = {login(loginInfo)},
            defaultErrorMessage = "Error logging in"
        )
    }
}