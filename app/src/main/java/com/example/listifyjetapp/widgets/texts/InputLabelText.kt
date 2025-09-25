package com.example.listifyjetapp.widgets.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.listifyjetapp.ui.theme.ListifyColor


@Composable
fun InputLabelText(
    text: String,
    isError: Boolean = false,
) {
    Text(
        text = text,
        style = if (isError) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
        color = if (isError) ListifyColor.errorRed else ListifyColor.labelGrey,
    )
}
