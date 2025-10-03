package com.example.listifyjetapp.widgets.bottomMenus

import android.util.Patterns
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.screens.lists.ListsViewModel
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.texts.InputLabelText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareToForm(
    viewModel: ListsViewModel,
    email: String,
    isEmailBlank: Boolean,
    emailHasError: Boolean,
    listName: String = "",
    onValueChange: (String) -> Unit,
    onShareClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        dragHandle = null,
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        onDismissRequest = { onDismissRequest() },

    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = listName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = email,
                    onValueChange = { onValueChange(it) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    label = { InputLabelText(text="Email") },
                    isError = isEmailBlank || emailHasError || viewModel.errorMessage != null ,
                    supportingText = {
                        when {
                            emailHasError -> InputLabelText(text = "Incorrect email format.", isError = emailHasError)
                            isEmailBlank -> InputLabelText(text = "Email cannot be empty.", isError = isEmailBlank)
                            viewModel.errorMessage != null -> InputLabelText(text = viewModel.errorMessage!!, isError = viewModel.errorMessage != null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, // ** Go to next **
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(onNext = { onShareClick() })
                )

                FilledButton(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = ButtonShape,
                    containerColor = ListifyColor.SplashYellow,
                    contentColor = ListifyColor.TextDark,
                    text = "Share",
                    onClick = { onShareClick() },
                )
            }
        }
    }
}