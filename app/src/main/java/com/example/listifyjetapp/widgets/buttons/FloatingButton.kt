package com.example.listifyjetapp.widgets.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun FloatingButton(onClick: () -> Unit = {}) {
    SmallFloatingActionButton(
        onClick = { onClick() },
        containerColor = ListifyColor.SplashYellow,
        contentColor = ListifyColor.TextDark
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add list item icon."
        )
    }
}