package com.example.listifyjetapp.components.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ValidatingInputTextField(
    email: String,
    onValueChange: (String) -> Unit,
    validatorHasError: Boolean
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end=16.dp, top = 16.dp),
        value = email,
        onValueChange = onValueChange,
        label = { Text("Email") },
        isError = validatorHasError,
        supportingText = {
            if (validatorHasError) {
                Text("Incorrect email format.")
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, // ** Go to next **
            keyboardType = KeyboardType.Email
        ),
    )
}