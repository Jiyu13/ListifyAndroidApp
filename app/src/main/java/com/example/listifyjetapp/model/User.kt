package com.example.listifyjetapp.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("created_at") val createdAt: String
)

data class SharedUsers(
    @SerializedName("user_id") val userId: Int,
    val username: String,
)

