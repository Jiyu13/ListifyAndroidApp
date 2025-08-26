package com.example.listifyjetapp.widgets.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.widgets.texts.InputLabelText
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun FormInputField(
    placerHolder: String,
    textState: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = ButtonShape,
        colors = TextFieldDefaults.colors(
            // Indicator colors for error state - bottom border to be red
            unfocusedIndicatorColor = if (isError) ListifyColor.errorRed else Color.White,
            focusedIndicatorColor = if (isError) ListifyColor.errorRed else Color.White,
            disabledIndicatorColor = Color.Transparent,

            // Label colors
            unfocusedLabelColor = if (isError) ListifyColor.errorRed else ListifyColor.TextDark,
            focusedLabelColor = if (isError) Color.Red else ListifyColor.TextDark,

            // Container colors
            unfocusedContainerColor = ListifyColor.TextDark.copy(0.1f),
            focusedContainerColor = ListifyColor.TextDark.copy(0.1f),
            cursorColor = if (isError) ListifyColor.errorRed else ListifyColor.TextDark,
        ),
        singleLine = true,
        maxLines = 1,

        value = textState,
        onValueChange = onValueChange,
        placeholder = { InputLabelText(text=placerHolder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),  // Sets the keyboard to normal text input.
//        isError = isError,
//        supportingText = {
//            if (isError) {
//                InputLabelText("Cannot be empty.")
//            }
//        }
    )

    if (isError) {
        InputLabelText(
            "Cannot be empty.",
            isError = isError,
        )
    }
}