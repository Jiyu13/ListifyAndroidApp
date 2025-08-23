package com.example.listifyjetapp.widgets.texts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputLabelText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
