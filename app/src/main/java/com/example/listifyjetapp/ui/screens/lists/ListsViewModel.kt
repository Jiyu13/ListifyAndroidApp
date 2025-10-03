package com.example.listifyjetapp.ui.screens.lists

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.model.ShareWithEmail
import com.example.listifyjetapp.repository.ListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: ListsRepository,
    private val storageManager: ListifyStorageManager
): ViewModel() {
    // handle auth errors
    private val _authError = MutableStateFlow(false)
    val authError = _authError.asStateFlow()

    val lists = mutableStateListOf<ListModel>()
    var isLoading by mutableStateOf(false)

    // null = closed; otherwise the list.id currently being edited/shared
    var editingListId = MutableStateFlow<Int?>(null)
        private set
    var sharingListId = MutableStateFlow<Int?>(null)
        private set
    fun openEdit(listId: Int) { editingListId.value = listId }
    fun closeEdit() { editingListId.value = null }
    fun openShare(listId: Int) { sharingListId.value = listId }
    fun closeShare() { sharingListId.value = null }

    var errorMessage by mutableStateOf<String?>(null)
    var isShareSucceed by mutableStateOf(false)

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack = _navigateBack.asStateFlow()

    // Convert Flow<Int> to StateFlow<Int>
    private val _userId = storageManager.userIdFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Keeps the flow alive while observed
        initialValue = 0
    )
    val userId: StateFlow<Int> = _userId

    fun getUserLists() {
        viewModelScope.launch {
            val userId = storageManager.getUser().first().userId
            isLoading = true
            errorMessage = null

            try {
                val result = repository.getUserLists(userId = userId)
                when (result) {
                    is ListifyResult.Success -> {
                        lists.clear()
                        lists.addAll(result.data)
                        _authError.value = false
                    }

                    is ListifyResult.Failure -> {
                        errorMessage = result.errorMessage
                        if (result.errorMessage.contains("401") ||
                            result.errorMessage.contains("token", ignoreCase = true)) {
                            _authError.value = true
                        }
                        Log.d("Fail to fetch lists by user id", result.toString())
                    }
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun insertListByUser(userId: Int, newListName: ListName)
    = viewModelScope.launch {
        isLoading = true
        val result = repository.insertListByUser(userId, newListName)
        when (result) {
            is ListifyResult.Success -> {
                _navigateBack.value = true
            }
            is ListifyResult.Failure -> Unit
        }
        isLoading = false
    }

    fun updateListName(listId: Int, newListName: ListName)
    = viewModelScope.launch {
        isLoading = true
        val result = repository.updateListName(listId, newListName)
        when (result) {
            is ListifyResult.Success -> {
                val updated = result.data
                lists.replaceAll { if(it.id == updated.id) updated else it}
                //_navigateBack.value = true
            }
            is ListifyResult.Failure -> Unit
        }
        isLoading = false
    }

    fun deleteListById(listId: Int)
    = viewModelScope.launch {
        isLoading = true
        val userId = storageManager.getUser().first().userId
        val result = repository.deleteListById(userId, listId)
        when (result) {
            is ListifyResult.Success -> {
                lists.removeAll { it.id == listId }
            }
            is ListifyResult.Failure -> Unit
        }
        isLoading = false
    }

    fun shareListById(listId: Int, email: String) = viewModelScope.launch {
        errorMessage = null
        val userId = storageManager.getUser().first().userId
        val result = repository.shareListById(listId, email, userId)
        when (result) {
            is ListifyResult.Success -> {
                // update list.share to be true, return updated list
                val updated = result.data
                lists.replaceAll { if(it.id == updated.id) updated else it}
                isShareSucceed = true
            }
            is ListifyResult.Failure -> {
                errorMessage = result.errorMessage
            }
        }
    }

    fun navigationComplete() {
        _navigateBack.value = false
    }
}