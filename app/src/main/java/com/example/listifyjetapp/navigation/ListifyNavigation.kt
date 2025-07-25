package com.example.listifyjetapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listifyjetapp.screens.forms.ListifyNewListScreen
import com.example.listifyjetapp.screens.lists.ListifyListsScreen
import com.example.listifyjetapp.screens.profile.ListifyProfileScreen
import com.example.listifyjetapp.screens.splash.ListifySplashScreen


@Composable
fun ListifyNavigation() {

    // Create a controller for navigating between screens
    val navController = rememberNavController()

    // Build the navigation graph
    NavHost(
        navController = navController,
        startDestination = ListifyScreens.SplashScreen.route
    ) {

        // TODO: Define a navigation route for SplashScreen
        composable(ListifyScreens.SplashScreen.route) {
            ListifySplashScreen(navController = navController)
        }

        // TODO: Define a navigation route for ListsScreen
        composable(ListifyScreens.ListsScreen.route) {
            ListifyListsScreen(navController = navController)
        }

        // TODO: Define a navigation route for NewListScreen
        composable(ListifyScreens.NewListScreen.route) {
            ListifyNewListScreen(navController = navController)
        }

        // TODO: Define a navigation route for DetailScreen
        composable(ListifyScreens.DetailScreen.route) {
            //ListifyDetailScreen(navController = navController)
        }

        // TODO: Define a navigation route for ProfileScreen
        composable(ListifyScreens.ProfileScreen.route) {
            //ListifyProfileScreen(navController = navController)
        }

        // TODO: Define a navigation route for LoginScreen
        composable(ListifyScreens.LoginScreen.route) {
            //ListifyLoginScreen(navController = navController)
        }

        // TODO: Define a navigation route for SignupScreen
        composable(ListifyScreens.SignupScreen.route) {
            //ListifySignupScreen(navController = navController)
        }
    }
}

