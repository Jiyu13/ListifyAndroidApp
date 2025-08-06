package com.example.listifyjetapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listifyjetapp.ui.screens.auth.ListifyLoginScreen
import com.example.listifyjetapp.ui.screens.lists.ListifyListsScreen
import com.example.listifyjetapp.ui.screens.newList.ListifyNewListScreen
import com.example.listifyjetapp.ui.screens.splash.ListifySplashScreen


@Composable
fun ListifyNavigation() {
    // Create a controller for navigating between screens
    val navController = rememberNavController()

    // Build the navigation graph
    NavHost(
        navController = navController,
        startDestination = ListifyScreens.SplashScreen
    ) {

        // TODO: Define a navigation route for SplashScreen
        composable<ListifyScreens.SplashScreen>() {
            ListifySplashScreen(navController = navController)
        }

        // TODO: Define a navigation route for ListsScreen
        composable<ListifyScreens.ListsScreen>() {
            ListifyListsScreen(navController = navController)
        }

        // TODO: Define a navigation route for NewListScreen
        composable<ListifyScreens.NewListScreen>() {
            ListifyNewListScreen(navController = navController)
        }

        // TODO: Define a navigation route for DetailScreen
        composable<ListifyScreens.DetailScreen>() {
            //ListifyDetailScreen(navController = navController)
        }

        // TODO: Define a navigation route for ProfileScreen
        composable<ListifyScreens.ProfileScreen>() {
            //ListifyProfileScreen(navController = navController)
        }

        // TODO: Define a navigation route for LoginScreen
        composable<ListifyScreens.LoginScreen>() {
            ListifyLoginScreen(navController = navController)
        }

        // TODO: Define a navigation route for SignupScreen
        composable<ListifyScreens.SignupScreen>() {
            //ListifySignupScreen(navController = navController)
        }
    }
}

