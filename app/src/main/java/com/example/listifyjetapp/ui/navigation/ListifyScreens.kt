package com.example.listifyjetapp.ui.navigation

import kotlinx.serialization.Serializable


object ListifyScreens {
    @Serializable object SplashScreen
    @Serializable data class ListsScreen(val userId: Int)
    @Serializable object NewListScreen
    @Serializable data class ListItemScreen(val listId: Int, val listName: String)
    @Serializable object AddNewItemScreen
    @Serializable object ProfileScreen
    @Serializable object LoginScreen
    @Serializable object SignupScreen
}