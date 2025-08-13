package com.example.listifyjetapp.ui.screens.lists

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.repository.ListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: ListsRepository,
    private val storageManager: ListifyStorageManager
): ViewModel() {
    val lists = mutableStateListOf<ListModel>()
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack = _navigateBack.asStateFlow()

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
                    }

                    is ListifyResult.Failure -> {
                        errorMessage = result.errorMessage
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

    fun navigationComplete() {
        _navigateBack.value = false
    }
}