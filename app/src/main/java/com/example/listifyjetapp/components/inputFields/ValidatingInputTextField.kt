package com.example.listifyjetapp.components.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.widgets.texts.InputLabelText

@Composable
fun ValidatingInputTextField(
    email: String,
    onValueChange: (String) -> Unit,
    validatorHasError: Boolean
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { InputLabelText(text="Email") },
        isError = validatorHasError,
        supportingText = {
            if (validatorHasError) {
                InputLabelText("Incorrect email format.")
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, // ** Go to next **
            keyboardType = KeyboardType.Email
        ),
    )
}