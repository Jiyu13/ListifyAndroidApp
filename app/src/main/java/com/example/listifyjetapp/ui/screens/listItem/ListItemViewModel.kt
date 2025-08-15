package com.example.listifyjetapp.ui.screens.listItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.repository.ListItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(
    private val repository: ListItemRepository
): ViewModel() {
    val listItems = mutableStateListOf<ListItem>()
    var isLoading  by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var activeItemId by mutableStateOf<Int?>(null)
    var activeItemDescription by mutableStateOf("")

    fun getAllItems(listId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val result = repository.getItemsByListId(listId)
                when (result) {
                    is ListifyResult.Success -> {
                        listItems.clear()
                        listItems.addAll(result.data)
                    }

                    is ListifyResult.Failure -> {
                        errorMessage = result.errorMessage
                        Log.d("Fail to fetch list items by list id", result.toString())
                    }
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun checkListItem(
        itemId: Int,
        listId: Int,
        updatedData: CheckedItem
    ) = viewModelScope.launch {
        val result = repository.checkListItem(listId = listId, itemId = itemId, updatedData = updatedData)
        when (result) {
            is ListifyResult.Success -> {
                val updated =  result.data
                listItems.replaceAll { if(it.id == updated.id) updated else it }
            }

            is ListifyResult.Failure -> {
                errorMessage = result.errorMessage
                Log.d("Fail to fetch list items by list id", result.toString())
            }
        }
    }
}