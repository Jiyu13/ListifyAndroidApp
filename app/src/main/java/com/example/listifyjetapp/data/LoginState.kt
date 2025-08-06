package com.example.listifyjetapp.data

import com.example.listifyjetapp.model.LoginSuccess

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginSuccess) : LoginState()
    data class Error(val message: String) : LoginState()
}