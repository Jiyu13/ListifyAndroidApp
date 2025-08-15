package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.network.ListifyAPI
import javax.inject.Inject

class ListItemRepository @Inject constructor(
    private val api: ListifyAPI
){
    suspend fun getItemsByListId(listId: Int): ListifyResult<List<ListItem>> {
        try {
            val response = api.getListItems(listId)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error fetching items by list id")
        }
    }

    suspend fun checkListItem(
        itemId: Int,
        listId: Int,
        updatedData: CheckedItem
    ): ListifyResult<ListItem> {
        try {
            val response = api.checkListItem(listId = listId, itemId = itemId, request = updatedData)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error checking item")
        }
    }

}