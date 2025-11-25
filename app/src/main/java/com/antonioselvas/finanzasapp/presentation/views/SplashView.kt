package com.antonioselvas.finanzasapp.presentation.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.antonioselvas.finanzasapp.R
import kotlinx.coroutines.delay
import primaryColor


const val SPLASH_ROUTE = "SplashView"


@Composable
fun SplashView(navController: NavController, isOnboardingComplete: Boolean?) {
    Scaffold(
        containerColor = primaryColor
    ) {
        SplashContent(it) {
            android.util.Log.d("SplashView", "isOnboardingComplete en Splash = $isOnboardingComplete")

            val completed = isOnboardingComplete
            if (completed == null) {
                return@SplashContent
            }

            val route = if (completed) "main_graph" else "onboarding_graph"
            navController.navigate(route) {
                popUpTo(SPLASH_ROUTE) { inclusive = true }
            }
        }
    }
}



@Composable
fun SplashContent(paddingValues: PaddingValues, onTimeout: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.finanzapp_animation)
    )

    // Captura siempre la última lambda pasada desde SplashView
    val currentOnTimeout by androidx.compose.runtime.rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        delay(2000)
        currentOnTimeout()
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .wrapContentSize()
    ) {
        LottieAnimation(composition = composition)
    }
}
