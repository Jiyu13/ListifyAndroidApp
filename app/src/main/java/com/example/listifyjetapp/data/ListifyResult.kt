package com.example.listifyjetapp.data

sealed class ListifyResult<T> {
    data class Success<T>(val data: T): ListifyResult<T>()
    data class Failure<T>(val errorMessage: String): ListifyResult<T>()
}