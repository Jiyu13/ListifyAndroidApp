package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyResult
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.model.UserWithoutPassword
import com.example.listifyjetapp.model.Username
import com.example.listifyjetapp.network.ListifyAPI
import com.example.listifyjetapp.utils.safeApiCall
import com.google.android.gms.tasks.Tasks.call
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    private val api: ListifyAPI,
    private val storageManager: ListifyStorageManager
){

    suspend fun updateUsername(
        userId: Int, newUsername: Username
    ): ListifyResult<UserWithoutPassword> {
        return api.safeApiCall(
            call = { patchUserById(userId = userId, newUsername) },
            defaultErrorMessage = "Error updating username"
        )

    }
}