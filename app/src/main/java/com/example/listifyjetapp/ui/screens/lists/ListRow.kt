package com.example.listifyjetapp.ui.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.components.formModals.EditListForm
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun ListRow(
    list: ListModel,
    viewModel: ListsViewModel = hiltViewModel(),
    onListRowClick: () -> Unit
) {
    val listName = if (list.name.length >= 30) list.name.substring(0, 20) + "..." else list.name
    var isEditFormShown  by remember { mutableStateOf(false) }
    var listNameState by remember(list) { mutableStateOf(list.name) }
    var isError by remember { mutableStateOf(false) }

    fun onEditFormDismiss() {
        isEditFormShown = !isEditFormShown
        listNameState = listName
    }

    fun onEditFormSubmit() {
        isError = false
        if (listNameState.isBlank()) {
            isError = true
        } else {
            //Log.d("new name", "$list.id, $listNameState")
            val updatedInfo = ListName(name = listNameState)
            viewModel.updateListName(listId = list.id, newListName = updatedInfo)
            isEditFormShown = false
        }
    }

    fun onDeleteItem() {
        viewModel.deleteListById(listId = list.id)
        isEditFormShown = false
    }

    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // List info
            Column(modifier = Modifier.fillMaxWidth(0.8f).clickable { onListRowClick() }) {
                Text(
                    text = listName,
                    color = ListifyColor.TextBlack,
                    style = MaterialTheme.typography.bodyMedium,
                )

                if (list.share) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            tint = ListifyColor.IconGreen,
                            imageVector = Icons.Default.Share,
                            contentDescription = "Shared with"
                        )
                        Text(
                            text = list.sharedWith.take(3).joinToString(", ") { it.username },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = ListifyColor.TextGrey,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Text(
                    text = list.createdAt,
                    color = ListifyColor.TextGrey,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            // List setting menu

            Row(
                modifier = Modifier.clickable { onEditFormDismiss() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = list.itemCount.toString(),
                    color = ListifyColor.TextGrey,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "More icon"
                )

            }

        }

    }

    if (isEditFormShown) {
        EditListForm(
            listName = listNameState,
            isError = isError,
            onListNameChange = { listNameState = it },
            onEditFormSubmit = { onEditFormSubmit() },
            onDeleteItem = {
                onDeleteItem()
            }
        )
    }
    HorizontalDivider()
}