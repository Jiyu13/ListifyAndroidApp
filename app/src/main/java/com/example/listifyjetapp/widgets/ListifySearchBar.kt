package com.example.listifyjetapp.widgets


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor


@Composable
fun ListifySearchBar(
    searchTextValue: MutableState<String>,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,              // what happen when you press "Next"
    keyboardAction: KeyboardActions = KeyboardActions.Default // what to do when an action is triggered
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            unfocusedLabelColor = ListifyColor.TextDark.copy(0.1f),
            focusedContainerColor = ListifyColor.TextDark.copy(0.1f),
            cursorColor = ListifyColor.TextDark,
        ),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
            )
        },

        value = searchTextValue.value,
        onValueChange = onValueChange,
        placeholder = { Text(text="Search") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),  // Sets the keyboard to normal text input.
        keyboardActions = keyboardAction,
    )
}