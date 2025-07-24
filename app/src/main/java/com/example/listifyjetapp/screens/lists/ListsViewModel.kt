package com.example.listifyjetapp.screens.lists

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.repository.ListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(private val repository: ListsRepository): ViewModel() {
    val lists = mutableStateListOf<ListModel>()
    var isLoading = mutableStateOf(false)

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
}