package com.example.listifyjetapp.ui.screens.auth

import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.data.LoginState
import com.example.listifyjetapp.model.LoginInfo
import com.example.listifyjetapp.model.UserDataStore
import com.example.listifyjetapp.repository.AuthUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthUserRepository,
    private val storageManager: ListifyStorageManager
): ViewModel() {

    var email by mutableStateOf("")
        private  set
    var password by mutableStateOf("")
        private set

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)


    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    fun updateEmail(newEmail:String) { email = newEmail }
    fun updatePassword(newPassword: String) { password = newPassword }

    fun login()
    = viewModelScope.launch {
        loginState = LoginState.Loading
        val result = repository.login( LoginInfo(email = email.trim(), password = password) )
        loginState = when (result) {
            is ListifyResult.Success -> {
                storageManager.saveToDataStore(
                    UserDataStore(
                        userId = result.data.user.id,
                        username = result.data.user.username,
                        email = result.data.user.email,
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
                        isLogin = true,
                    )
                )
                // Save token securely
                LoginState.Success(result.data)
            }

            is ListifyResult.Failure -> {
                LoginState.Error(result.errorMessage)
            }
        }
    }
}