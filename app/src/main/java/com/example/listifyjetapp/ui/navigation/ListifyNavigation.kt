package com.example.listifyjetapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.listifyjetapp.data.NavBarItems
import com.example.listifyjetapp.ui.screens.auth.ListifyLoginScreen
import com.example.listifyjetapp.ui.screens.listItem.ListifyListItemScreen
import com.example.listifyjetapp.ui.screens.listItem.ListifyNewItemScreen
import com.example.listifyjetapp.ui.screens.lists.ListifyListsScreen
import com.example.listifyjetapp.ui.screens.newList.ListifyNewListScreen
import com.example.listifyjetapp.ui.screens.splash.ListifySplashScreen
import com.example.listifyjetapp.ui.theme.ListifyColor


@Composable
fun ListifyNavigation() {
    // Create a controller for navigating between screens
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    // Hide bottom bar on Splash/Login/Signup (typed routes = qualified class names)
    val hideOnRoutes = remember {
        setOf(
            ListifyScreens.SplashScreen::class.qualifiedName!!,
            ListifyScreens.LoginScreen::class.qualifiedName!!,
            ListifyScreens.SignupScreen::class.qualifiedName!!,
            ListifyScreens.NewListScreen::class.qualifiedName!!,
            ListifyScreens.ListItemScreen::class.qualifiedName!!,
            ListifyScreens.AddNewItemScreen::class.qualifiedName!!
        )
    }
    val hideBottomBar =
        currentDestination?.hierarchy?.any { dest -> dest.route in hideOnRoutes } == true

    Scaffold(
        bottomBar = {
            if (!hideBottomBar) {
                NavigationBar(
                    modifier = Modifier.height(72.dp),
                    windowInsets = NavigationBarDefaults.windowInsets
                ) {
                    NavBarItems.entries.forEach { barItem ->
                        // Selected if we are on the tab's string route or its typed screen
                        val selected = currentDestination?.hierarchy?.any { dest ->
                            when (barItem) {
                                NavBarItems.Lists -> dest.route == barItem.route ||
                                        dest.route == ListifyScreens.ListsScreen::class.qualifiedName!!
                                NavBarItems.Profile -> dest.route == barItem.route ||
                                        dest.route == ListifyScreens.ProfileScreen::class.qualifiedName!!
                            }
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally,) {
                                    Box {
                                        Icon(
                                            imageVector = if (selected) barItem.selectedIcon else barItem.icon,
                                            contentDescription = barItem.title,
                                            modifier = Modifier.size(28.dp),
                                            tint = if (selected) ListifyColor.TextDark else ListifyColor.TextDark.copy(.5f)
                                        )
                                    }
                                    Spacer(Modifier.height(0.dp))
                                    Text(
                                        text = barItem.title,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (selected) ListifyColor.TextDark else ListifyColor.TextDark.copy(.5f),
                                        modifier = Modifier.offset(y = (-3).dp),
                                        maxLines = 1
                                    )
                                }
                            },
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent // <-- no pill
                            ),
                            onClick = {
                                navController.navigate(barItem.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(),
            navController = navController,
            startDestination = ListifyScreens.SplashScreen
        ) {
            // ------------------------------Typed routes (kotlinx.serialization) ------------------
            // TODO: Define a navigation route for SplashScreen
            composable<ListifyScreens.SplashScreen>() {
                ListifySplashScreen(
                    onNavigateToListsScreen = { userId ->
                        navController.navigate(ListifyScreens.ListsScreen(userId)) {   // Use typed ListsScreen when coming from Splash (has userId)
                            popUpTo(ListifyScreens.SplashScreen) { inclusive = true }  // Remove Splash from the back stack when go to Lists Screen
                            launchSingleTop = true
                        }
                    },
                    onGoToLoginScreen = { navController.navigate(ListifyScreens.LoginScreen) }
                )
            }

            // TODO: Define a navigation route for ListsScreen
            composable<ListifyScreens.ListsScreen>() {
                ListifyListsScreen(
                    onListRowClick = {listId, listName ->
                        navController.navigate(ListifyScreens.ListItemScreen(listId, listName))
                    },
                    onAddNewListClick = {navController.navigate(ListifyScreens.NewListScreen)},
                    onNavigateToSplash = {navController.navigate(ListifyScreens.SplashScreen)},
                )
            }

            // TODO: Define a navigation route for NewListScreen
            composable<ListifyScreens.NewListScreen>() {
                ListifyNewListScreen(onPopBackStack = { navController.popBackStack() })
            }

            // TODO: Define a navigation route for DetailScreen
            composable<ListifyScreens.ListItemScreen> {backStackEntry ->
                val args = backStackEntry.toRoute<ListifyScreens.ListItemScreen>()
                val listId = args.listId
                val listName = args.listName
                ListifyListItemScreen(
                    listId = listId,
                    listName = listName,
                    onPopBackStack = { navController.popBackStack() },
                    onAddClick = { navController.navigate(ListifyScreens.AddNewItemScreen(listId)) }
                )
            }

            // TODO: Define a navigation route for NewItemScreen
            composable<ListifyScreens.AddNewItemScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<ListifyScreens.AddNewItemScreen>()
                val listId = args.listId
                ListifyNewItemScreen(
                    listId = listId,
                    onPopBackStack = { navController.popBackStack() },
                )
            }

            // TODO: Define a navigation route for LoginScreen
            composable<ListifyScreens.LoginScreen>() {
                ListifyLoginScreen(
                    onPopBackStack = { navController.popBackStack() },
                    onNavigateToListsScreen = { userId -> navController.navigate(ListifyScreens.ListsScreen(userId)) }
                )
            }

            // TODO: Define a navigation route for SignupScreen
            composable<ListifyScreens.SignupScreen>() {
                //ListifySignupScreen(navController = navController)
                Text("Signup")
            }

            // TODO: Define a navigation route for ProfileScreen
            composable<ListifyScreens.ProfileScreen>() {
                //ListifyProfileScreen(navController = navController)
                Text("Profile")
            }
            // -------------------------------------------------------------------------------------

            // ------------------------- String routes for bottom-bar tabs -------------------------
            composable(NavBarItems.Lists.route) {
                // Same UI as ListsScreen; ViewModel can read userId from DataStore if needed
                ListifyListsScreen(
                    onListRowClick = { listId, listName ->
                        navController.navigate(ListifyScreens.ListItemScreen(listId, listName))
                    },
                    onAddNewListClick = { navController.navigate(ListifyScreens.NewListScreen) },
                    onNavigateToSplash = { navController.navigate(ListifyScreens.SplashScreen) }
                )
            }

            composable(NavBarItems.Profile.route) {
                Text("Profile")
            }
        }
    }
}

