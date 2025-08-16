package com.example.listifyjetapp.components.buttomMenus

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.ui.screens.listItem.ListItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemActionSheet(
    onDismissSheet: () -> Unit,
    viewModel: ListItemViewModel = hiltViewModel()
) {
    ModalBottomSheet(
        onDismissRequest =  onDismissSheet,
        modifier = Modifier.fillMaxWidth(),
    ) {

        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            textAlign = TextAlign.Center,
            text = viewModel.activeItemDescription,
            fontSize = 20.sp
        )

        ListItem(
            headlineContent = { Text(text = "Edit", fontSize = 20.sp) },
            leadingContent = { Icon(Icons.Default.Edit, null) }
        )
        ListItem(
            headlineContent = { Text(text = "Delete", fontSize = 20.sp) },
            leadingContent = { Icon(Icons.Default.Delete, null) }
        )
    }
}