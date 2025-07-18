package com.example.listifyjetapp.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.ui.theme.barriecitoFont
import kotlinx.coroutines.delay

@Composable
fun ListifySplashScreen(navController: NavHostController) {

    // TODO: Create an Animated object that holds a Float value starting at 0f
    val scale = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = true, block = { // key1 = true ensure it runs once only
        scale.animateTo(
            targetValue = 0.8f,           // scale from 0f to 0.9f
            animationSpec = tween(         // animation timing,
                durationMillis = 800,      // time based interpolation of 800ms
                easing = {OvershootInterpolator(8f).getInterpolation(it)}  // a "bounce" effect
            )
        )

        // when the animation is over, delay 2s before going to next screen
        delay(2000L)
        // TODO: Navigate to MainScreen
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
                text = "Listify",
                color = ListifyColor.TextDark,
                fontFamily = barriecitoFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 96.sp,
                modifier = Modifier.scale(scale.value)
            )
            Text(
                text = "From To-Dos to Ta-Das!",
                color = ListifyColor.TextGrey
            )
        }
    }

}
