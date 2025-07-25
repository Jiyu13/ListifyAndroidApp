package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.network.ListifyAPI
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
}