package com.antonioselvas.finanzasapp.presentation.navManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.dataStores.StoreOnBoarding
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HomeView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.AddBalanceView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.BALANCE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GOAL_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GoalView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WELCOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SELECT_CATEGORY_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SELECT_FIXED_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SelectCategoriesView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SelectFixedView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WelcomeView

@Composable
fun NavManager() {
    val context = LocalContext.current
    val dataStore = StoreOnBoarding(context)
    val isOnboardingComplete = dataStore.getOnBoarding.collectAsState(true)
    val navController = rememberNavController()

    val startRoute = if (isOnboardingComplete.value) {
        "main_graph"
    } else {
        "onboarding_graph"
    }
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        navigation(
            startDestination = WELCOME_ROUTE,
            route = "onboarding_graph"
        ) {
            composable(WELCOME_ROUTE) { WelcomeView(navController) }

            composable(GOAL_ROUTE) { GoalView(navController) }

            composable(BALANCE_ROUTE) { AddBalanceView(navController) }

            composable(SELECT_CATEGORY_ROUTE) { SelectCategoriesView(navController) }

            composable(SELECT_FIXED_ROUTE) {
                SelectFixedView(
                    navController = navController,
                    onComplete = {
                        navController.navigate("main_graph") {
                            popUpTo("onboarding_graph") { inclusive = true }
                        }
                    })
            }
        }

        navigation(
            startDestination = MAIN_NAV_ROUTE,
            route = "main_graph"
        ){
            composable(
                MAIN_NAV_ROUTE
            ) {
                MainNavManager()
            }
        }





    }

}