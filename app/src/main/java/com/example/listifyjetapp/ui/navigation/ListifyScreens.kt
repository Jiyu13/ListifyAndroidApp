package com.example.listifyjetapp.ui.navigation

import kotlinx.serialization.Serializable


object ListifyScreens {
    @Serializable object SplashScreen
    @Serializable data class ListsScreen(val userId: Int)
    @Serializable object DetailScreen
    @Serializable object ProfileScreen
    @Serializable object LoginScreen
    @Serializable object SignupScreen
    @Serializable object NewListScreen
}