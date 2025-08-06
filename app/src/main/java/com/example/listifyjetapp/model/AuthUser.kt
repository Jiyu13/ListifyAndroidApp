package com.example.listifyjetapp.model

import com.google.gson.annotations.SerializedName

data class UserToken(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    val token: String,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("created_at") val createdAt: String
)

data class LoginInfo(
    val email: String,
    val password: String,
)

data class LoginSuccess(
    val message: String,
    val token: String,
    val user:  UserWithoutPassword
)