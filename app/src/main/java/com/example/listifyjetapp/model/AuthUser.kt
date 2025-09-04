package com.example.listifyjetapp.model

import com.google.gson.annotations.SerializedName

data class UserToken(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    val token: String,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("created_at") val createdAt: String
)

data class UserDataStore(
    val userId: Int,
    val username: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean = false,
)

data class LoginInfo(
    val email: String,
    val password: String,
)

data class LoginSuccess(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val user:  UserWithoutPassword
)

data class SignupInfo(
    val email: String,
    val username: String,
    val password: String,
)