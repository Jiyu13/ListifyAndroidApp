package com.example.listifyjetapp.model

import com.google.gson.annotations.SerializedName

data class BaseList(
    val id: Int,
    val name: String,
    val share: Boolean,
    //val sharedCode: String,  // can be UUID or String
    //val createdAt: LocalDateTime
    @SerializedName("shared_code") val sharedCode: String,
    @SerializedName("created_at") val createdAt: String,
)

data class ListName (
    val name: String
)

data class ListModel(
    val id: Int,
    val name: String,
    val share: Boolean,
    @SerializedName("shared_code") val sharedCode: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("shared_with") val sharedWith: List<SharedUsers>
)

data class ListItem(
    val id: Int,
    val description: String,
    val units: String,
    val checked: Boolean,
    @SerializedName("list_id") val listId: Int,
)
