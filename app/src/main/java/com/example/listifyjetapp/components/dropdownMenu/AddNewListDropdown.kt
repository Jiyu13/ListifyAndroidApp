package com.example.listifyjetapp.components.dropdownMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddNewListDropdown(
    isDropdownExpanded: Boolean,
    onAddNewList: () -> Unit = {},
    onJoinSharedList: () -> Unit = {},
    onDropdownDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { onDropdownDismiss() }
        ) {
            DropdownMenuItem(
                text = { Text("Create New List") },
                onClick = {
                    onAddNewList()
                    onDropdownDismiss()
                }
            )
            DropdownMenuItem(
                text = { Text("Join Shared List") },
                onClick = { onJoinSharedList() }
            )
        }
    }
}