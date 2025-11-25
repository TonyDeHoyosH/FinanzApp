package com.antonioselvas.finanzasapp.presentation.navManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.dataStores.StoreOnBoarding
import com.antonioselvas.finanzasapp.presentation.views.SPLASH_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.SplashView
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
    val isOnboardingCompleteState = dataStore.getOnBoarding.collectAsState(initial = null)
    val navController = rememberNavController()
    val isOnboardingComplete = isOnboardingCompleteState.value
    android.util.Log.d("NavManager", "isOnboardingComplete (state) = $isOnboardingComplete")

    NavHost(
        navController = navController,
        startDestination = "splash_graph"
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
                    },
                    store = dataStore
                )
            }
        }

        navigation(
            startDestination = MAIN_NAV_ROUTE,
            route = "main_graph"
        ) {
            composable(MAIN_NAV_ROUTE) { MainNavManager() }
        }

        navigation(
            startDestination = SPLASH_ROUTE,
            route = "splash_graph"
        ) {
            composable(SPLASH_ROUTE) {
                SplashView(
                    navController = navController,
                    isOnboardingComplete = isOnboardingComplete
                )
            }
        }
    }
}


