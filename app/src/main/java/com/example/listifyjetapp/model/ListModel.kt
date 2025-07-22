package com.example.listifyjetapp.model

import java.time.LocalDateTime

data class ListModel(
    val id: Int,
    val name: String,
    val share: Boolean,
    val shareCode: String,  // can be UUID or String
    val createdAt: LocalDateTime
)
