package com.example.listifyjetapp.widgets.inputFields

import android.R.attr.label
import android.R.attr.textStyle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.listifyjetapp.widgets.texts.InputLabelText


@Composable
fun OutlinedInput(
    inputText: String,
    label: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputText,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { InputLabelText(text=label) },
        isError = isError,
        supportingText = {
            if (isError) {
                InputLabelText(errorMessage)
            }
        },
        keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next, // ** Go to next **
        ),
    )
}