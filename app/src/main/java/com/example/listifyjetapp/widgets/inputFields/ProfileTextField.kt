package com.example.listifyjetapp.widgets.inputFields

import android.R.attr.label
import android.R.attr.maxLines
import android.R.attr.singleLine
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(
    textState: String,
    placeholder: String = "",
    label: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var isShowPassword by remember { mutableStateOf(false) }


    Column (Modifier.fillMaxWidth().padding(8.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .offset(x=(-16).dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            visualTransformation = when {
                !isPassword -> VisualTransformation.None
                isShowPassword -> VisualTransformation.None
                else -> PasswordVisualTransformation()
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { isShowPassword = !isShowPassword }) {
                        Icon(
                            imageVector = if (isShowPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isShowPassword) "Hide password" else "Show password"
                        )
                    }
                } else null
           },
            singleLine = true, // Replaces lineLimits = 1
            maxLines = 1,
            //placeholder = {
            //    Text(
            //        text = placeholder,
            //        color = ListifyColor.TextGrey.copy(.5f),
            //        //style = MaterialTheme.typography.bodyMedium
            //    )
            //},
            label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
            value = textState,
            onValueChange = onValueChange,
        )
    }
}