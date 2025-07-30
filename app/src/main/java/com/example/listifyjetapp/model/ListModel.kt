package com.example.listifyjetapp.model

import com.google.gson.annotations.SerializedName

data class ListModel(
    val id: Int,
    val name: String,
    val share: Boolean,
    //val sharedCode: String,  // can be UUID or String
    //val createdAt: LocalDateTime
    @SerializedName("shared_code") val sharedCode: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("shared_with") val sharedWith: List<SharedUsers>
)
