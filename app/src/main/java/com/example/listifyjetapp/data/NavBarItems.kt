package com.example.listifyjetapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.listifyjetapp.ui.navigation.ListifyScreens

enum class NavBarItems(
    val screen: Any,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    Lists(
        screen = ListifyScreens.ListsTab,
        title = "Lists",
        icon = Icons.AutoMirrored.Filled.FormatListBulleted,
        selectedIcon = Icons.AutoMirrored.Filled.FormatListBulleted,
    ),

    Profile(
        screen = ListifyScreens.ProfileTab,
        title = "You",
        icon = Icons.Default.PersonOutline,
        selectedIcon = Icons.Default.Person,
    )
}