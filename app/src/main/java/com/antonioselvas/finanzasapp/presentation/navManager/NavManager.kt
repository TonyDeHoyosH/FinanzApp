package com.antonioselvas.finanzasapp.presentation.navManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.dataStores.StoreOnBoarding
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.AddBalanceView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.BALANCE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GOAL_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GoalView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.ONBOARDING_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WelcomeView

@Composable
fun NavManager(){
    val context = LocalContext.current
    val dataStore = StoreOnBoarding(context)
    val store = dataStore.getOnBoarding.collectAsState(false)
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
                GoalView(navController)
            }

            composable(
                BALANCE_ROUTE
            ) {
                AddBalanceView()
            }


        }

}