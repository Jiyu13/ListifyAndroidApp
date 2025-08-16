package com.example.listifyjetapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.listifyjetapp.ui.screens.auth.ListifyLoginScreen
import com.example.listifyjetapp.ui.screens.listItem.ListifyListItemScreen
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
            ListifySplashScreen(
                onNavigateToListsScreen = {userId ->
                    navController.navigate(ListifyScreens.ListsScreen(userId)) {
                        // Remove Splash from the back stack when go to Lists Screen
                        popUpTo(ListifyScreens.SplashScreen) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onGoToLoginScreen = { navController.navigate(ListifyScreens.LoginScreen) }

            )
        }

        // TODO: Define a navigation route for ListsScreen
        composable<ListifyScreens.ListsScreen>() {
            ListifyListsScreen(
                onListRowClick = {listId, listName -> navController.navigate(ListifyScreens.ListItemScreen(listId, listName))},
                onRightButtonClick = {navController.navigate(ListifyScreens.NewListScreen)}
            )
        }

        // TODO: Define a navigation route for NewListScreen
        composable<ListifyScreens.NewListScreen>() {
            ListifyNewListScreen(
                onPopBackStack = { navController.popBackStack() },
            )
        }

        // TODO: Define a navigation route for DetailScreen
        composable<ListifyScreens.ListItemScreen> {backStackEntry ->
            val args = backStackEntry.toRoute<ListifyScreens.ListItemScreen>()
            val listId = args.listId
            val listName = args.listName
            ListifyListItemScreen(
                listId = listId,
                listName = listName,
                onPopBackStack = { navController.popBackStack() }
            )
        }

        // TODO: Define a navigation route for ProfileScreen
        composable<ListifyScreens.ProfileScreen>() {
            //ListifyProfileScreen(navController = navController)
        }

        // TODO: Define a navigation route for LoginScreen
        composable<ListifyScreens.LoginScreen>() {
            ListifyLoginScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigateToListsScreen = {userId -> navController.navigate(ListifyScreens.ListsScreen(userId))}
            )
        }

        // TODO: Define a navigation route for SignupScreen
        composable<ListifyScreens.SignupScreen>() {
            //ListifySignupScreen(navController = navController)
        }
    }
}

