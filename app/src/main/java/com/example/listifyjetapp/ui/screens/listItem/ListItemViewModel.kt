package com.example.listifyjetapp.ui.screens.listItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.BasicItemInfo
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

    fun insertListItem(
        listId: Int,
        newItem: BasicItemInfo
    ) = viewModelScope.launch {
        isLoading = true
        val result = repository.insertListItem(listId = listId, newItem = newItem)
        when (result) {
            is ListifyResult.Success -> {
                val item =  result.data
                listItems.add(item)
            }

            is ListifyResult.Failure -> {
                errorMessage = result.errorMessage
                Log.d("Fail to add a new item to list $listId", result.toString())
            }
        }
        isLoading = false
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
                Log.d("Fail to update item checked state", result.toString())
            }
        }
    }

    fun patchListItemInfo(
        itemId: Int,
        listId: Int,
        updatedInfo: BasicItemInfo
    ) = viewModelScope.launch {
        val result = repository.patchListItem(listId = listId, itemId = itemId, updatedInfo = updatedInfo)
        when (result) {
            is ListifyResult.Success -> {
                val updated =  result.data
                listItems.replaceAll { if(it.id == updated.id) updated else it }
            }

            is ListifyResult.Failure -> {
                errorMessage = result.errorMessage
                Log.d("Fail to update item description and units", result.toString())
            }
        }
    }

    fun deleteListItem(listId: Int, itemId: Int) = viewModelScope.launch{
        val result = repository.deleteListItem(listId = listId, itemId = itemId)
        when (result) {
            is ListifyResult.Success -> {
                listItems.clear()
                listItems.addAll(result.data)
            }

            is ListifyResult.Failure -> {
                errorMessage = result.errorMessage
                Log.d("Fail to delete item.", result.toString())
            }
        }
    }
}