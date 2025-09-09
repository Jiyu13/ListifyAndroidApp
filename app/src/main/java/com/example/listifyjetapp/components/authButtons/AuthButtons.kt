package com.example.listifyjetapp.components.authButtons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ButtonPaddings
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.CustomOutlinedButton
import com.example.listifyjetapp.widgets.buttons.FilledButton

@Composable
fun AuthButtons(
    onSignupClick: () -> Unit,
    onGoToLoginScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedButton(
            modifier = ButtonPaddings.fillMaxWidth(),
            shape = ButtonShape,
            border = BorderStroke(2.dp, color = Color.White),
            text = "SIGN UP WITH EMAIL",
            textColor = ListifyColor.TextDark,
            buttonIcon = Icons.Default.MailOutline,
            iconDescription = "Sign In Email",
            onClick = { onSignupClick() }
        )

        FilledButton(
            modifier = ButtonPaddings.fillMaxWidth(),
            shape = ButtonShape,
            containerColor = Color.White,
            contentColor = ListifyColor.TextDark,
            text = "LOG IN",
            buttonIcon = Icons.AutoMirrored.Filled.ExitToApp,
            iconDescription = "Log In",
            onClick = { onGoToLoginScreen() }
        )
    }
}