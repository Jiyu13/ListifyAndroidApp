package com.example.listifyjetapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavBarItems(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    Lists(
        route = "lists",
        title = "Lists",
        icon = Icons.AutoMirrored.Filled.FormatListBulleted,
        selectedIcon = Icons.AutoMirrored.Filled.FormatListBulleted,
    ),

    Profile(
        route = "profile",
        title = "You",
        icon = Icons.Default.PersonOutline,
        selectedIcon = Icons.Default.Person,
    )
}