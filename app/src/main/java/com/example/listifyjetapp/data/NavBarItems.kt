package com.example.listifyjetapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavBarItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Lists(
        route = "lists",
        title = "Lists",
        icon = Icons.AutoMirrored.Filled.List
    ),

    Profile(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}