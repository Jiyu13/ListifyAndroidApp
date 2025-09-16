package com.example.listifyjetapp.repository

import androidx.datastore.core.IOException
import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.model.ShareWithEmail
import com.example.listifyjetapp.network.ListifyAPI
import com.example.listifyjetapp.utils.parseErrorMessage
import retrofit2.HttpException
import javax.inject.Inject


class ListsRepository @Inject constructor(private val api: ListifyAPI) {
    suspend fun getUserLists(userId: Int): ListifyResult<List<ListModel>> {
        try {
            val response = api.getListsByUser(userId)
            return ListifyResult.Success(data = response)
        } catch (e: Exception) {
            return ListifyResult.Failure(e.message ?: "Error fetching lists")
        }
    }

    suspend fun insertListByUser(userId: Int, newListData: ListName): ListifyResult<ListModel> {
        try {
            val response = api.insertList(userId, newListData)
            val formatedResponse = ListModel (
                id = response.id,
                name = response.name,
                share = response.share,
                sharedCode = response.sharedCode,
                createdAt = response.createdAt,
                itemCount = 0,
                sharedWith= emptyList()
            )
            return ListifyResult.Success(data = formatedResponse)
        } catch (e:Exception) {
            return ListifyResult.Failure(e.message ?: "Error creating new list")
        }
    }

    suspend fun updateListName(listId: Int, newListData: ListName): ListifyResult<ListModel> {
        try {
            val response = api.updateListName(listId, newListData)
            val formatedResponse = ListModel (
                id = response.id,
                name = response.name,
                share = response.share,
                sharedCode = response.sharedCode,
                createdAt = response.createdAt,
                itemCount = 0,
                sharedWith= emptyList()
            )
            return ListifyResult.Success(data = formatedResponse)
        } catch (e:Exception) {
            return ListifyResult.Failure(e.message ?: "Error updating list name")
        }
    }


    suspend fun deleteListById(userId: Int, listId: Int): ListifyResult<Unit> {
        try {
            val response = api.deleteListByUserId(userId, listId)
            return ListifyResult.Success(data = response)
        } catch (e:Exception) {
            return ListifyResult.Failure(e.message ?: "Error deleting list name")
        }
    }

    suspend fun shareListById(listId: Int, email: String, userId: Int): ListifyResult<ListModel> {
        try {
            val request = ShareWithEmail(email = email, userId = userId)
            val response = api.shareAList(listId = listId, request = request)
            return ListifyResult.Success(data = response)
        } catch(e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = parseErrorMessage(errorBody) ?: "Failed to share list $listId"
            return ListifyResult.Failure(errorMessage)
        } catch (e: IOException) {
            // Handle network errors
            return ListifyResult.Failure("Network error: ${e.message}")
        } catch (e:Exception) {
            val errorBody = e.message
            val errorMessage = parseErrorMessage(errorBody) ?: "Failed to share list $listId"
            return ListifyResult.Failure(errorMessage)
        }
    }
}