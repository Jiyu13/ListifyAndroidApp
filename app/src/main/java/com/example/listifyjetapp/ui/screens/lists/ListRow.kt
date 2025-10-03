package com.example.listifyjetapp.ui.screens.lists

import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.components.formModals.EditListForm
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.bottomMenus.ShareToForm

@Composable
fun ListRow(
    list: ListModel,
    viewModel: ListsViewModel = hiltViewModel(),
    onListRowClick: () -> Unit
) {
    Log.d("list", list.toString())

    val editingId by viewModel.editingListId.collectAsState()
    val sharingId by viewModel.sharingListId.collectAsState()

    val listName = if (list.name.length >= 30) list.name.substring(0, 20) + "..." else list.name
    var listNameState by remember(list) { mutableStateOf(list.name) }
    var isError by remember { mutableStateOf(false) }

    // =========================== share form ======================================================
    var email by remember { mutableStateOf("") }
    var isEmailBlank by remember { mutableStateOf(false) }
    val emailHasError by remember { derivedStateOf{
        if (email.isNotEmpty()) {
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    } }

    fun onShareClick() {
        if (email.isBlank()) {
            isEmailBlank = true
        } else {
            viewModel.shareListById(list.id, email)
            viewModel.closeShare()
        }
    }

    val content = LocalContext.current
    LaunchedEffect(viewModel.isShareSucceed) {
        if (viewModel.isShareSucceed) {
            Toast.makeText(content, "Share successfully", Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0)}
                .show()
            viewModel.isShareSucceed = false // Reset
        }
    }


    // =========================== Edit form =======================================================
    fun onEditFormDismiss() {
        viewModel.closeEdit()
        listNameState = listName
        isError = false
    }

    fun onEditFormSubmit() {
        isError = false
        if (listNameState.isBlank()) {
            isError = true
        } else {
            //Log.d("new name", "$list.id, $listNameState")
            val updatedInfo = ListName(name = listNameState)
            viewModel.updateListName(listId = list.id, newListName = updatedInfo)
            viewModel.closeEdit()
        }
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

            Row(
                modifier = Modifier.clickable { onEditFormDismiss() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${list.checkedItems}/${list.itemCount}",
                    color = ListifyColor.TextGrey,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 16.dp)
                )

            }

        }

    }

    if (editingId == list.id) {
        EditListForm(
            listName = listNameState,
            isError = isError,
            onListNameChange = { listNameState = it },
            onEditFormSubmit = { onEditFormSubmit() },
            onDismissRequest = { viewModel.closeEdit() }
        )
    }

    if (sharingId == list.id) {
        ShareToForm(
            viewModel = viewModel,
            email = email.toString(),
            isEmailBlank = isEmailBlank,
            emailHasError = emailHasError,
            listName = list.name,
            onValueChange = {it ->
                email = it
                isEmailBlank = false
                viewModel.errorMessage = null
            },
            onShareClick = { onShareClick() },
            onDismissRequest = {
                viewModel.closeShare()
                email = ""
                isEmailBlank = false
                viewModel.errorMessage = null
            },
        )
    }
}