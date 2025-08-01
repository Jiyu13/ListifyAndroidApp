package com.example.listifyjetapp.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.listifyjetapp.components.inputFields.PasswordTextField
import com.example.listifyjetapp.widgets.ListifyTopBar
import com.example.listifyjetapp.components.inputFields.ValidatingInputTextField

@Composable
fun ListifyLoginScreen(
    navController: NavHostController,
) {

    val emailViewModel: EmailViewModel = viewModel<EmailViewModel>()
    val password = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "LOG IN",
            isListsScreen = false,
            onGoBackButtonClicked = {navController.popBackStack()},
            leftText = "CANCEL",
        ) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ValidatingInputTextField(
                    email = emailViewModel.email,
                    onValueChange = { input -> emailViewModel.updateEmail(input) },
                    validatorHasError = emailViewModel.emailHasErrors
                )

                PasswordTextField(
                    password = password.value,
                    onPasswordChange = { password.value = it }
                )

            }
        }
    }
}