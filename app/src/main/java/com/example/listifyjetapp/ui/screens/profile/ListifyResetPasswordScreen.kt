package com.example.listifyjetapp.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.inputFields.ProfileTextField


@Composable
fun ListifyResetPasswordScreen(
    onGoBackButtonClicked: () -> Unit
) {

    var currantPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    fun onSaveClick() {}

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Reset password",
            isListsScreen = false,
            goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = {onGoBackButtonClicked},
            rightText = "Save",
            onRightButtonClick = { onSaveClick() }
        ) }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                ProfileTextField(
                    textState = currantPassword,
                    label = "Current password",
                    isResetPasswordScreen = true,
                    onValueChange = { currantPassword = it }
                )

                HorizontalDivider(thickness = 1.dp)

                ProfileTextField(
                    textState = newPassword,
                    label = "New password",
                    isResetPasswordScreen = true,
                    onValueChange = { newPassword = it }
                )

                HorizontalDivider(thickness = 1.dp)

                ProfileTextField(
                    textState = confirmPassword,
                    label = "Confirm password",
                    isResetPasswordScreen = true,
                    onValueChange = { confirmPassword = it }
                )

                HorizontalDivider(thickness = 1.dp)

            }
        }
    }
}