package com.example.listifyjetapp.navigation

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