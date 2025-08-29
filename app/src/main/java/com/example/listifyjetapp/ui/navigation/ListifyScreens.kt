package com.example.listifyjetapp.ui.navigation

import com.example.listifyjetapp.model.BasicItemInfo
import kotlinx.serialization.Serializable


object ListifyScreens {
    @Serializable object SplashScreen
    @Serializable data class ListsScreen(val userId: Int)
    @Serializable object NewListScreen
    @Serializable data class ListItemScreen(val listId: Int, val listName: String)
    @Serializable data class AddNewItemScreen(val listId: Int)
    @Serializable object ProfileScreen
    @Serializable object LoginScreen
    @Serializable object SignupScreen

    // Bottom-bar tabs (roots, no args)
    @Serializable object ListsTab
    @Serializable object ProfileTab
}