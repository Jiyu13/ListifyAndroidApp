package com.example.listifyjetapp.ui.screens.lists

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.repository.ListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(private val repository: ListsRepository): ViewModel() {
    val lists = mutableStateListOf<ListModel>()
    var isLoading = mutableStateOf(false)

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack = _navigateBack.asStateFlow()

    fun getUserLists(userId: Int) {
        viewModelScope.launch {
                isLoading.value = true
                val result = repository.getUserLists(userId = userId)
                when (result) {
                    is ListifyResult.Success -> {
                        lists.clear()
                        lists.addAll(result.data)
                    }

                    is ListifyResult.Failure -> Unit
                }
                isLoading.value = false
        }
    }

    fun insertListByUser(userId: Int, newListName: ListName)
    = viewModelScope.launch {
        isLoading.value = true
        val result = repository.insertListByUser(userId, newListName)
        when (result) {
            is ListifyResult.Success -> {
                _navigateBack.value = true
            }
            is ListifyResult.Failure -> Unit
        }
        isLoading.value = false
    }

    fun navigationComplete() {
        _navigateBack.value = false
    }
}