package com.example.listifyjetapp.ui.navigation

enum class ListifyScreens(val route: String) {
    SplashScreen("splash"),
    ListsScreen("lists"),
    DetailScreen("detail"),
    ProfileScreen("profile"),
    LoginScreen("login"),
    SignupScreen("signup"),
    NewListScreen("new-list")
}

// navController.navigate(ListifyScreens.LoginScreen.route)