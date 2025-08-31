package com.example.listifyjetapp.repository

import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.model.Username
import com.example.listifyjetapp.network.ListifyAPI
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    private val api: ListifyAPI,
    private val storageManager: ListifyStorageManager
){

    suspend fun updateUserInfo(newUserInfo: Username) {

    }
}