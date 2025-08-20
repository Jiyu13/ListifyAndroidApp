package com.example.listifyjetapp.network

import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.model.LoginInfo
import com.example.listifyjetapp.model.LoginSuccess
import com.example.listifyjetapp.model.BasicItemInfo
import com.example.listifyjetapp.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListifyAPI {
    // =============================================== Auth ========================================
    @POST("auth/login")
    suspend fun login(@Body request: LoginInfo): LoginSuccess

    @POST("refresh")
    suspend fun refresh(@Header("x-refresh-token") refreshToken: String): LoginSuccess

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
    )

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
    suspend fun insertList(
        @Path("user_id") userId: Int,
        @Body request: ListName
    ): ListModel

    // Get items by list id
    @GET("lists/{list_id}")
    suspend fun getListItems(@Path("list_id") listId: Int): List<ListItem>

    // update a list
    @PATCH("lists/{list_id}")
    suspend fun updateListName(
        @Path("list_id") listId: Int,
        @Body request: ListName
    ): ListModel

    // Post new item
    @POST("lists/{list_id}/add-item")
    suspend fun createListItem(
        @Path("list_id") listId: Int,
        @Body request: BasicItemInfo
    ): ListItem

    // Patch list item
    @PATCH("lists/{list_id}/{item_id}")
    suspend fun checkListItem(
        @Path("list_id") listId: Int,
        @Path("item_id") itemId: Int,
        @Body request: CheckedItem
    ):ListItem

    @PATCH("lists/{list_id}/{item_id}")
    suspend fun patchListItem(
        @Path("list_id") listId: Int,
        @Path("item_id") itemId: Int,
        @Body request: BasicItemInfo
    ):ListItem

    // Delete list item
    @DELETE("lists/{list_id}/{item_id}")
    suspend fun deleteListItem(
        @Path("list_id") listId: Int,
        @Path("item_id") itemId: Int
    ): List<ListItem>
}