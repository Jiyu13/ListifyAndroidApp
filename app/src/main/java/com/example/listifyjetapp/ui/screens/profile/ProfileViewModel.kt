package com.example.listifyjetapp.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
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

    var isUpdateFail by mutableStateOf(false)
    var isUpdateSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

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
}