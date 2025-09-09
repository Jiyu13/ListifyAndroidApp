package com.example.listifyjetapp.ui.screens.profile

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.ui.theme.ButtonPaddings
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.dividers.InputDivider
import com.example.listifyjetapp.widgets.inputFields.ProfileTextField
import com.example.listifyjetapp.widgets.texts.InputLabelText


@Composable
fun ListifyResetPasswordScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onGoBackButtonClicked: () -> Unit
) {
    val content = LocalContext.current
    LaunchedEffect(viewModel.isUpdateSuccess) {
        if (viewModel.isUpdateSuccess) {
            Toast.makeText(content, "Password reset successfully.", Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0)}
                .show()
            viewModel.isUpdateSuccess = false // Reset
        }
    }

    fun onSaveClick() {

        if (viewModel.currentPw.isBlank()) {
            viewModel.isCurrentError = true
            viewModel.errorMessage = "Cannot be empty."
        }
        if (viewModel.newPw.isBlank()) {
            viewModel.isNewError = true
            viewModel.errorMessage = "Cannot be empty."

        }
        if (viewModel.confirmPw.isBlank()) {
            viewModel.isConfirmError = true
            viewModel.errorMessage = "Cannot be empty."
        }

        if (viewModel.newPw.isNotBlank() && viewModel.newPw != viewModel.confirmPw) {
            viewModel.errorMessage = "Two password fields don't match."
            viewModel.isNewError = true
            viewModel.isConfirmError = true
        }
        if (viewModel.newPw.isNotBlank() && viewModel.newPw == viewModel.confirmPw) {
            viewModel.resetPassword()
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Reset password",
            isListsScreen = false,
            goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = {onGoBackButtonClicked()},
            //rightText = "Save",
            //onRightButtonClick = { onSaveClick() }
        ) }
    ) { innerPadding ->

        Surface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Column(modifier = Modifier.fillMaxWidth().padding(16.dp),) {
                ProfileTextField(
                    textState = viewModel.currentPw,
                    label = "Current password",
                    isPassword = true,
                    onValueChange = {
                        viewModel.currentPw = it
                        viewModel.isCurrentError = false
                        viewModel.isNewError = false
                        viewModel.isConfirmError = false
                    }
                )

                InputDivider(viewModel.isCurrentError)
                if (viewModel.isCurrentError) {
                    InputLabelText(
                        viewModel.errorMessage,
                        isError = viewModel.isCurrentError,
                    )
                }

                ProfileTextField(
                    textState = viewModel.newPw,
                    label = "New password",
                    isPassword = true,
                    onValueChange = {
                        viewModel.newPw = it
                        viewModel.isCurrentError = false
                        viewModel.isNewError = false
                        viewModel.isConfirmError = false
                    }
                )

                InputDivider(viewModel.isNewError)
                if (viewModel.isNewError) {
                    InputLabelText(
                        viewModel.errorMessage,
                        isError = viewModel.isNewError,
                    )
                }

                ProfileTextField(
                    textState = viewModel.confirmPw,
                    label = "Confirm password",
                    isPassword = true,
                    onValueChange = {
                        viewModel.confirmPw = it
                        viewModel.isNewError = false
                        viewModel.isConfirmError = false
                    }
                )

                InputDivider(viewModel.isConfirmError)
                if (viewModel.isConfirmError) {
                    InputLabelText(
                        viewModel.errorMessage,
                        isError = viewModel.isConfirmError,
                    )
                }

                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                    FilledButton(
                        modifier = ButtonPaddings.fillMaxWidth(),
                        shape = RoundedCornerShape(3.dp),
                        containerColor=ListifyColor.SplashYellow,
                        contentColor = ListifyColor.TextDark,
                        text="Save",
                        onClick={ onSaveClick() }
                    )
                }
            }
        }
    }
}