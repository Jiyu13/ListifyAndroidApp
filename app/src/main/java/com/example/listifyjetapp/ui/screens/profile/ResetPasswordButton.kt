package com.example.listifyjetapp.ui.screens.profile

import android.R.attr.text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ResetPasswordButton(
    goToReSetPW: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).clickable{ goToReSetPW() },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = "Reset Password"
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "Go to Reset Password",
            modifier = Modifier.size(20.dp)
        )
    }
}