package com.example.listifyjetapp.network

import androidx.room.Update
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.User
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListifyAPI {
    // =============================================== Users =======================================
    @PATCH("users/{user_id}")
    suspend fun patchUserById(@Path("user_id") userId: Int): User

    @POST("users")
    suspend fun createUser(): User

    @GET("users/check_username/{username}")
    suspend fun getUserByUsername(@Path("/username") username: String ): User

    @GET("users/email/{email}")
    suspend fun getUserByUserEmail(@Path("/email") email: String ): User

    @GET("users/users")
    suspend fun getAllUsers(): List<User>

    // =============================================== User Lists ==================================
    @GET("ul/shared-user/{list_id}/{user_id}")
    suspend fun getSharedUsers(
        @Path("user_id") userId: Int,
        @Path("list_id") listId: Int
    ): List<User>

    @GET("ul/{user_id}")
    suspend fun getListsByUser(@Path("user_id") userId: Int): List<ListModel>


    @DELETE("ul/{user_id}/{list_id}")
    suspend fun deleteListByUserId(
        @Path("user_id") userId: Int,
        @Path("list_id") listId: Int
    ) {}

    @POST("ul/{list_id}/{share_with_id}")
    suspend fun shareAList(
        @Path("list_ud") listId: Int,
        @Path("share_with_id") shareWithId: Int ){

    }

    // =============================================== Lists =======================================
    // get all lists
    @GET("lists")
    suspend fun getLists(): List<ListModel>

    // Post new list
    @POST("lists/{user_id}")
    suspend fun createList(@Path("user_id") userId: Int) {}

    // Get items by list id
    @GET("lists/{list_id}")
    suspend fun getAList(@Path("list_id") listId: Int) {}

    // update a list
    @POST("lists/{list_id}")
    suspend fun updateList(@Path("list_id") listId: Int) {}

    // Post new item
    @POST("lists/{list_id}/add-item")
    suspend fun createListItem(@Path("list_id") listId: Int) {}

    // Patch list item
    @POST("lists/{list_id}/{item_id}")
    suspend fun patchListItem(
        @Path("list_id") listId: Int,
        @Path("item_id") itemId: Int
    ) {}

    // Delete list item
    @DELETE("/lists//{item_id}")
    suspend fun deleteListItem(
        @Path("list_id") listId: Int,
        @Path("item_id") itemId: Int
    ) {}
}