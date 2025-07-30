package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.network.ListifyAPI
import javax.inject.Inject


class ListsRepository@Inject constructor(private val api: ListifyAPI){

    suspend fun getUserLists(userId: Int): ListifyResult<List<ListModel>> {
        try {
            val response = api.getListsByUser(userId)
            //Log.d("userLists", "userLists: $response")
            return ListifyResult.Success(data = response)
        } catch (e:Exception) {
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
}