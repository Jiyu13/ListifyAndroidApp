package com.example.listifyjetapp.ui.screens.profile

import android.R.attr.thickness
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.listifyjetapp.R
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.listifyjetapp.data.LoginState
import com.example.listifyjetapp.ui.theme.ButtonPaddings
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.buttons.TextButton
import com.example.listifyjetapp.widgets.dialogs.AlertDialogPopup
import com.example.listifyjetapp.widgets.dividers.InputDivider
import com.example.listifyjetapp.widgets.inputFields.ProfileTextField
import com.example.listifyjetapp.widgets.texts.InputLabelText

@Composable
fun ListifyProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    goToReSetPW: () -> Unit,
    goToSplash: () -> Unit,
) {

    val username by viewModel.username.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()

    var usernameInput by rememberSaveable(username) { mutableStateOf(username) }
    //var emailInput by rememberSaveable(email) { mutableStateOf(email) }

    var isLogoutClicked by remember { mutableStateOf(false) }
    var isDeleteClicked by remember { mutableStateOf(false) }
    val deleteAccountState = viewModel.loginState


    val content = LocalContext.current
    LaunchedEffect(viewModel.isUpdateSuccess) {
        if (viewModel.isUpdateSuccess) {
            Toast.makeText(content, "Username updated.", Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0)}
                .show()
            viewModel.isUpdateSuccess = false // Reset
        }
    }

    fun onSaveUsername() {
        if (usernameInput.isEmpty()) {
            viewModel.isUpdateFail = true
            viewModel.errorMessage = "Cannot be empty."
        } else {
            viewModel.updateUsername(usernameInput)
        }
    }

    fun confirmLogout() {
        isLogoutClicked = false
        viewModel.logout()
        goToSplash()
    }

    fun confirmDelete() {
        isDeleteClicked = false
        viewModel.deleteUser()
    }

    fun onCloseDialog() {
        viewModel.loginState = LoginState.Idle
        viewModel.logout()
        goToSplash()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Profile",
            isListsScreen = false,
        ) }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = email,
                    style = MaterialTheme.typography.labelLarge,
                    color = ListifyColor.TextDark.copy(.5f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ProfileTextField(
                        textState = usernameInput,
                        placeholder ="Enter your username",
                        label = "Username",
                        onValueChange = {
                            usernameInput = it
                            viewModel.isUpdateFail = false
                        }
                    )

                    InputDivider(viewModel.isUpdateFail)

                    if (viewModel.isUpdateFail) {
                        InputLabelText(
                            viewModel.errorMessage,
                            isError = viewModel.isUpdateFail,
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                        FilledButton(
                            modifier = ButtonPaddings.fillMaxWidth(),
                            shape = RoundedCornerShape(3.dp),
                            containerColor=ListifyColor.SplashYellow,
                            contentColor = ListifyColor.TextDark,
                            text="Save",
                            onClick={ onSaveUsername() }
                        )
                    }

                    HorizontalDivider(thickness = 1.dp)

                    ResetPasswordButton(goToReSetPW = goToReSetPW)

                    HorizontalDivider(thickness = 1.dp)

                    TextButton(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        buttonText = "Log out",
                        isClicked = { isLogoutClicked = true }
                    )

                    HorizontalDivider(thickness = 1.dp)

                    TextButton(
                        icon = Icons.Default.Delete,
                        buttonText = "Delete Account",
                        isClicked = { isDeleteClicked = true }
                    )

                    HorizontalDivider(thickness = 1.dp)

                    //ProfileTextField(
                    //    textState = emailInput,
                    //    placeholder ="Enter your mail",
                    //    label = "Email",
                    //    onValueChange = { emailInput = it }
                    //)

                }
            }

            if (isLogoutClicked) {
                AlertDialogPopup(
                    title = "",
                    text = stringResource(R.string.log_out),
                    dismissButtonText = "Cancel",
                    confirmButtonText = "OK",
                    onDismissRequest = {  isLogoutClicked = false },
                    onConfirmation = { confirmLogout() }
                )
            }

            if (isDeleteClicked) {
                AlertDialogPopup(
                    title = "",
                    text = stringResource(R.string.delete_account),
                    dismissButtonText = "Cancel",
                    confirmButtonText = "OK",
                    onDismissRequest = {  isDeleteClicked = false },
                    onConfirmation = { confirmDelete() }
                )
            }

            if (deleteAccountState is LoginState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            when (deleteAccountState) {
                is LoginState.Success -> {
                    AlertDialogPopup(
                        title = "",
                        text = "Account deleted.",
                        dismissButtonText = "",
                        confirmButtonText = "OK",
                        onDismissRequest = { },
                        onConfirmation = { onCloseDialog() }
                    )
                }
                is LoginState.Error -> {
                    AlertDialogPopup(
                        title = "",
                        text = deleteAccountState.message,
                        dismissButtonText = "",
                        confirmButtonText = "OK",
                        onDismissRequest = { isDeleteClicked },
                        onConfirmation = { isDeleteClicked = false }
                    )
                }
                LoginState.Loading, LoginState.Idle -> { /* no dialog */ }
            }
        }
    }
}