package com.example.listifyjetapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.components.inputFields.PasswordTextField
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.components.inputFields.ValidatingInputTextField
import com.example.listifyjetapp.data.LoginState
import com.example.listifyjetapp.ui.theme.ButtonPaddings
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.FilledButton

@Composable
fun ListifyLoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    onNavigateToListsScreen: (userId: Int) -> Unit
) {

    val loginState = loginViewModel.loginState

    fun onLoginClick() {
        if (loginViewModel.email.isNotBlank() && loginViewModel.password.isNotBlank()) {
            loginViewModel.login()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "LOG IN",
            isListsScreen = false,
            onGoBackButtonClicked = {onPopBackStack()},
            leftText = "CANCEL",
        ) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, end = 16.dp, bottom = 0.dp, start = 16.dp)
                ) {
                    ValidatingInputTextField(
                        email = loginViewModel.email,
                        onValueChange = { input -> loginViewModel.updateEmail(input) },
                        validatorHasError = loginViewModel.emailHasErrors
                    )

                    PasswordTextField(
                        password = loginViewModel.password,
                        onPasswordChange = loginViewModel::updatePassword,
                        loginState = loginState
                    )
                }

                // handle login state
                if (loginState is LoginState.Success){
                    LaunchedEffect(Unit) { onNavigateToListsScreen(loginState.data.user.id) }
                }
                if (loginState is LoginState.Error) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(ListifyColor.errorRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = loginState.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                        )
                    }
                }

                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp, horizontal = 16.dp)) {
                    FilledButton(
                        modifier = ButtonPaddings.fillMaxWidth(),
                        shape = RoundedCornerShape(3.dp),
                        containerColor=ListifyColor.SplashYellow,
                        contentColor = ListifyColor.TextDark,
                        text="LOG IN ",
                        buttonIcon=Icons.AutoMirrored.Filled.ExitToApp,
                        iconDescription="Log In",
                        onClick={ onLoginClick() }
                    )
                }

            }
        }

    }
}