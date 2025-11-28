package com.antonioselvas.finanzasapp.presentation.views

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
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.antonioselvas.finanzasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import primaryColor


const val SPLASH_ROUTE = "SplashView"


@Composable
fun SplashView(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Scaffold(containerColor = primaryColor) {
        SplashContent(it) {
            if (user == null) {
                navController.navigate("onboarding_graph") {
                    popUpTo(SPLASH_ROUTE) { inclusive = true }
                }
            } else {
                FirebaseFirestore.getInstance().collection("Users").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        val completed = document?.getBoolean("onboardingCompleted") ?: false
                        val route = if (completed) "main_graph" else "onboarding_graph"
                        navController.navigate(route) {
                            popUpTo(SPLASH_ROUTE) { inclusive = true }
                        }
                    }
                    .addOnFailureListener {
                        navController.navigate("onboarding_graph") {
                            popUpTo(SPLASH_ROUTE) { inclusive = true }
                        }
                    }
            }
        }
    }
}




@Composable
fun SplashContent(paddingValues: PaddingValues, onTimeout: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.finanzapp_animation)
    )


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
