package com.example.listifyjetapp.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.listifyjetapp.R
import com.example.listifyjetapp.ui.navigation.ListifyScreens
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.ui.theme.barriecitoFont
import kotlinx.coroutines.delay

@Composable
fun ListifySplashScreen(navController: NavHostController) {
    // TODO: Create an Animated object that holds a Float value starting at 0f
    val scale = remember { Animatable(initialValue = 0f) }
    val isShowButtons = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true, block = { // key1 = true ensure it runs once only
        scale.animateTo(
            targetValue = 0.8f,           // scale from 0f to 0.9f
            animationSpec = tween(         // animation timing,
                durationMillis = 800,      // time based interpolation of 800ms
                easing = { OvershootInterpolator(8f).getInterpolation(it) }  // a "bounce" effect
            )
        )

        // when the animation is over, delay 2s before going to next screen
        delay(2000L)
        // TODO: Navigate to MainScreen
        //navController.navigate(ListifyScreens.ListsScreen.route)

        // TODO: Show login + SignIn button at the bottom
        isShowButtons.value = true

    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ListifyColor.SplashYellow,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_title),
                color = ListifyColor.TextDark,
                fontFamily = barriecitoFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 96.sp,
                modifier = Modifier.scale(scale.value)
            )
            Text(
                text = stringResource(R.string.app_tagline),
                color = ListifyColor.TextGrey
            )
        }

        if (isShowButtons.value) {
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
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        modifier = Modifier.size(20.dp),
                        contentDescription = "Log In"
                    )

                    Text(
                        text = "LOG IN ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
    }
}
