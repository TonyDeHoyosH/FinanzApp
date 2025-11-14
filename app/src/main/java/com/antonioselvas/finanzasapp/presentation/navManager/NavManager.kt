package com.antonioselvas.finanzasapp.presentation.navManager

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GOAL_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GoalView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.ONBOARDING_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WelcomeView

@Composable
fun NavManager(){
    val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ONBOARDING_ROUTE
        ){
            composable(
                ONBOARDING_ROUTE
            ){
                WelcomeView(navController)
            }

            composable(
                GOAL_ROUTE
            ) {
                GoalView()
            }


        }

}