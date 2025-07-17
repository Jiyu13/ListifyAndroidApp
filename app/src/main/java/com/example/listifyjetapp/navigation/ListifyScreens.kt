package com.example.listifyjetapp.navigation

enum class ListifyScreens(val route: String) {
    SplashScreen("splash"),
    ListsScreen("lists"),
    DetailScreen("detail"),
    ProfileScreen("profile"),
    LoginScreen("login"),
    SignupScreen("signup")
}

// navController.navigate(ListifyScreens.LoginScreen.route)