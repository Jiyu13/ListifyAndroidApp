package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.UpdateItemInfo
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

    suspend fun patchListItem(
        itemId: Int,
        listId: Int,
        updatedInfo: UpdateItemInfo
    ): ListifyResult<ListItem> {
        try {
            val response = api.patchListItem(listId = listId, itemId = itemId, request = updatedInfo)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error checking item")
        }
    }

    suspend fun deleteListItem( itemId: Int, listId: Int ): ListifyResult<List<ListItem>> {
        try {
            val response = api.deleteListItem(listId = listId, itemId = itemId)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error deleting item by item id")
        }
    }

}