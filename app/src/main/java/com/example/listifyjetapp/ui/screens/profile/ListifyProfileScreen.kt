package com.example.listifyjetapp.ui.screens.profile

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DividerDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.inputFields.ProfileTextField
import com.example.listifyjetapp.widgets.texts.InputLabelText

@Composable
fun ListifyProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    goToReSetPW: () -> Unit,
) {

    val username by viewModel.username.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()

    var usernameInput by rememberSaveable(username) { mutableStateOf(username) }
    var isUsernameEmpty by remember { mutableStateOf(false) }
    var isUpdateSuccess by remember { mutableStateOf(false) }
    //var emailInput by rememberSaveable(email) { mutableStateOf(email) }

    val content = LocalContext.current
    LaunchedEffect(isUpdateSuccess) {
        if (isUpdateSuccess) {
            Toast.makeText(content, "Username updated.", Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0)}
                .show()
            isUpdateSuccess = false // Reset
        }
    }

    fun onSaveUsername() {
        if (usernameInput.isEmpty()) {
            isUsernameEmpty = true
        } else {
            viewModel.updateUsername(usernameInput)
            isUpdateSuccess = true
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Profile",
            isListsScreen = false,
            rightText = "Save",
            onRightButtonClick = { onSaveUsername() }
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
                            isUsernameEmpty = false
                        }
                    )

                    HorizontalDivider(thickness = 1.dp, color = if (isUsernameEmpty) ListifyColor.errorRed else DividerDefaults.color)
                    if (isUsernameEmpty) {
                        InputLabelText(
                            "Cannot be empty.",
                            isError = isUsernameEmpty,
                        )
                    }

                    ResetPasswordButton(goToReSetPW = goToReSetPW)

                    HorizontalDivider(thickness = 1.dp)

                    //ProfileTextField(
                    //    textState = emailInput,
                    //    placeholder ="Enter your mail",
                    //    label = "Email",
                    //    onValueChange = { emailInput = it }
                    //)

                    //Spacer(modifier = Modifier.size(16.dp))

                    //FilledButton(
                    //    modifier = ButtonPaddings.fillMaxWidth(),
                    //    shape = ButtonShape,
                    //    containerColor = ListifyColor.SplashYellow,
                    //    contentColor = Color.White,
                    //   text = "Save",
                    //    buttonIcon = null,
                    //    iconDescription = null,
                    //    onClick = {  }
                    //)
                }
            }
        }
    }
}