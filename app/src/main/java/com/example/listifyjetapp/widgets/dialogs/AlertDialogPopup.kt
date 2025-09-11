package com.example.listifyjetapp.widgets.dialogs

import android.R.attr.onClick
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertDialogPopup(
    title: String,
    text: String,
    dismissButtonText: String,
    confirmButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmation:  () -> Unit,

) {
    AlertDialog(
        //title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                content = {Text(confirmButtonText)},
                onClick = { onConfirmation() }
            )
        },
        dismissButton = {
            TextButton(
                content = {Text(dismissButtonText)},
                onClick = { onDismissRequest() }
            )
        }
    )
}