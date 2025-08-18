package com.example.listifyjetapp.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyList(string: String) {

    Text(
        text = string,
        modifier = Modifier.padding(vertical = 16.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}