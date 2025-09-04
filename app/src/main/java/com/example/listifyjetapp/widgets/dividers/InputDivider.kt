package com.example.listifyjetapp.widgets.dividers

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun InputDivider(isDividerRed: Boolean) {
    HorizontalDivider(
        thickness = 1.dp,
        color = if (isDividerRed) ListifyColor.errorRed else DividerDefaults.color
    )

}