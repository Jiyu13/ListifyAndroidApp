package com.example.listifyjetapp.components.authButtons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.listifyjetapp.ui.navigation.ListifyScreens
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun AuthButtons(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, color = Color.White),

            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                modifier = Modifier.size(20.dp),
                contentDescription = "Sign In Email",
            )
            Text(
                text = "SIGN UP WITH EMAIL",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,

                )
        }

        Button(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = ListifyColor.TextDark
            ),
            onClick = {
                navController.navigate(ListifyScreens.LoginScreen.route)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                modifier = Modifier.size(20.dp),
                contentDescription = "Log In"
            )

            Text(
                text = "LOG IN ",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}