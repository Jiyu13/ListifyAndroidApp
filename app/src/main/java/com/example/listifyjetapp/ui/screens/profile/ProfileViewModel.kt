package com.example.listifyjetapp.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.data.LoginState
import com.example.listifyjetapp.model.Passwords
import com.example.listifyjetapp.model.Username
import com.example.listifyjetapp.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val storageManager: ListifyStorageManager

): ViewModel() {

    val username: StateFlow<String> = storageManager.usernameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )

    val email: StateFlow<String> = storageManager.emailFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )

    var currentPw by mutableStateOf("")
    var newPw by mutableStateOf("")
    var confirmPw by mutableStateOf("")
    var isCurrentError by mutableStateOf(false)
    var isNewError by mutableStateOf(false)
    var isConfirmError by mutableStateOf(false)

    var isUpdateFail by mutableStateOf(false)
    var isUpdateSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)

    fun updateUsername(newUsername:String) = viewModelScope.launch {
        val result = repository.updateUsername(
            userId = storageManager.userIdFlow.first(),
            Username(username = newUsername)
        )
        when (result) {
            is ListifyResult.Success -> {
                val updated = result.data
                storageManager.updateUsername(updated.username)
                isUpdateSuccess = true
            }
            is ListifyResult.Failure -> {
                isUpdateFail = true
                errorMessage = result.errorMessage
            }
        }
    }

    fun resetPassword() = viewModelScope.launch {
        val result = repository.resetPassword(
            userId = storageManager.userIdFlow.first(),
            Passwords(
                current = currentPw,
                new = newPw
            )
        )

        when (result) {
            is ListifyResult.Success -> {
                val updated = result.data
                isUpdateSuccess = true
                currentPw = ""
                newPw = ""
                confirmPw = ""
            }
            is ListifyResult.Failure -> {
                val err = result.errorMessage
                if (err.contains("Wrong password")) {
                    isCurrentError = true
                    errorMessage = err
                } else if (err.contains("New password must be different.")) {
                    isCurrentError = true
                    isNewError = true
                    errorMessage = err
                } else {
                    isUpdateFail = true
                    errorMessage = err
                }
            }
        }
    }

    fun logout() = viewModelScope.launch { storageManager.clearDataStore() }

    fun deleteUser() = viewModelScope.launch {
        loginState = LoginState.Loading
        val result = repository.deleteUser(storageManager.userIdFlow.first())
        loginState = when (result) {
            is ListifyResult.Success -> {
                LoginState.Success(result.data)
            }

            is ListifyResult.Failure -> {
                LoginState.Error( result.errorMessage )
            }
        }
    }
}