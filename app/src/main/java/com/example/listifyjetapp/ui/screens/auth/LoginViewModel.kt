package com.example.listifyjetapp.ui.screens.auth

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.LoginState
import com.example.listifyjetapp.model.LoginInfo
import com.example.listifyjetapp.model.UserWithoutPassword
import com.example.listifyjetapp.repository.AuthUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthUserRepository
): ViewModel() {

    var email by mutableStateOf("")
        private  set
    var password by mutableStateOf("")
        private set

    private val _currentUser = mutableStateOf<UserWithoutPassword?>(null)
    val currentUser = _currentUser

    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState = _loginState


    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }
    fun updateEmail(newEmail:String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) { password = newPassword }

    fun login()
    = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        val result = repository.login(
            LoginInfo(email = email.trim(), password = password)
        )

        _loginState.value = when (result) {
            is ListifyResult.Success -> {
                _currentUser.value = result.data.user
                // Save token securely
                LoginState.Success(result.data)
            }

            is ListifyResult.Failure -> {
                LoginState.Error(result.errorMessage)
            }
        }
    }
}