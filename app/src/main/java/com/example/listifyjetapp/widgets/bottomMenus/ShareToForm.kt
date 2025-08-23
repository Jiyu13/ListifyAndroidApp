package com.example.listifyjetapp.widgets.bottomMenus

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.model.ShareWithEmail
import com.example.listifyjetapp.ui.screens.lists.ListsViewModel
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.texts.InputLabelText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareToForm(
    viewModel: ListsViewModel = hiltViewModel(),
    listId: Int,
    closeShareForm: () -> Unit,
) {

    var email by remember { mutableStateOf("12345@gmail.com") }
    var isEmailBlank by remember { mutableStateOf(false) }
    val emailHasError by remember { derivedStateOf{
        if (email.isNotEmpty()) {
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    } }

    fun onAddUserClick() {
        if (email.isBlank()) {
            isEmailBlank = true
        } else {
            viewModel.shareListById(listId, ShareWithEmail(email))
        }
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        dragHandle = null,
        containerColor = Color.White,
        onDismissRequest = {
            closeShareForm()
            email = ""
            isEmailBlank = false
            viewModel.errorMessage = null
        },

    ) {
        Column (modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                text = "Shared to",
                style = MaterialTheme.typography.bodyMedium
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailBlank = false
                        viewModel.errorMessage = null
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    label = { InputLabelText(text="Email") },
                    isError = isEmailBlank || emailHasError || viewModel.errorMessage != null ,
                    supportingText = {
                        when {
                            emailHasError -> InputLabelText(text = "Incorrect email format.")
                            isEmailBlank -> InputLabelText(text = "Email cannot be empty.",)
                            viewModel.errorMessage != null -> InputLabelText(text = viewModel.errorMessage!!,)
                            else -> null
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, // ** Go to next **
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { onAddUserClick() }
                    )
                )

                FilledButton(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = ButtonShape,
                    containerColor = ListifyColor.SplashYellow,
                    contentColor = Color.White,
                    text = "Add user",
                    onClick = { onAddUserClick() },
                )
            }
        }
    }
}