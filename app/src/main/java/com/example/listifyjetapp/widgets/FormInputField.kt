package com.example.listifyjetapp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun FormInputField(
    textState: MutableState<String>,
    onValueChange: (String) -> Unit,
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            disabledIndicatorColor = Color.Transparent,

            unfocusedLabelColor = ListifyColor.TextDark,
            unfocusedContainerColor = ListifyColor.TextDark.copy(0.1f),
            focusedContainerColor = ListifyColor.TextDark.copy(0.1f),
            cursorColor = ListifyColor.TextDark,
        ),
        singleLine = true,
        maxLines = 1,

        value = textState.value,
        onValueChange = onValueChange,
        placeholder = { Text(text="e.g., grocery list") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),  // Sets the keyboard to normal text input.
    )
}