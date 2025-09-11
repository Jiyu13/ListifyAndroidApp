package com.example.listifyjetapp.ui.navigation

import kotlinx.serialization.Serializable


object ListifyScreens {
    @Serializable data class SplashScreen(val fromLogout: Boolean = false)  // accept a flag "fromlogout"
    @Serializable object LoginScreen
    @Serializable object SignupScreen

    //@Serializable data class ListsScreen(val userId: Int)
    //@Serializable object ProfileScreen

    @Serializable object ResetPasswordScreen
    @Serializable object NewListScreen
    @Serializable data class ListItemScreen(val listId: Int, val listName: String)
    @Serializable data class AddNewItemScreen(val listId: Int)


    // Bottom-bar tabs (roots, no args)
    @Serializable object ListsTab
    @Serializable object ProfileTab

    // Container for the main (tabbed) area
    @Serializable object Main
}