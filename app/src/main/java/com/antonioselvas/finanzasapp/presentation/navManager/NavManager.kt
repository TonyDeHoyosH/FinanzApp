package com.antonioselvas.finanzasapp.presentation.navManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.components.Alert
import com.antonioselvas.finanzasapp.presentation.viewModels.AuthViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.HomeViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.OnboardingViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitAccountViewModel
import com.antonioselvas.finanzasapp.presentation.views.LOGIN_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.LoginView
import com.antonioselvas.finanzasapp.presentation.views.REGISTER_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.RegisterView
import com.antonioselvas.finanzasapp.presentation.views.SPLASH_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.SplashView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.AddBalanceView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.BALANCE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GOAL_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GoalView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SELECT_CATEGORY_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SELECT_FIXED_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SelectCategoriesView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.SelectFixedView
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WELCOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WelcomeView

@Composable
fun NavManager(loginVM: AuthViewModel) {
    val onboardingVM = remember { OnboardingViewModel() }
    val rootNavController = rememberNavController()


    NavHost(
        navController = rootNavController,
        startDestination = "splash_graph"
    ) {
        navigation(
            startDestination = WELCOME_ROUTE,
            route = "onboarding_graph"
        ) {
            composable(WELCOME_ROUTE) { WelcomeView(rootNavController) }
            composable(REGISTER_ROUTE) { RegisterView(rootNavController, loginVM) }
            composable(LOGIN_ROUTE) { LoginView(rootNavController, loginVM) }
            composable(GOAL_ROUTE) { GoalView(rootNavController) { goal ->
                onboardingVM.tempGoal = goal
                rootNavController.navigate(BALANCE_ROUTE)
            }}
            composable(BALANCE_ROUTE) { AddBalanceView(rootNavController){ balance ->
                onboardingVM.tempInitialBalance = balance
                rootNavController.navigate(SELECT_CATEGORY_ROUTE)
            } }
            composable(SELECT_CATEGORY_ROUTE) { SelectCategoriesView(rootNavController){ categories ->
                onboardingVM.tempCategories = categories.toList()
                rootNavController.navigate(SELECT_FIXED_ROUTE)
            } }
            composable(SELECT_FIXED_ROUTE) {
                var showError by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf("") }

                if (showError) {
                    Alert(
                        title = "Error de guardado",
                        message = errorMessage,
                        confirmText = "Aceptar",
                        onConfirmClick = {
                            showError = false
                            rootNavController.navigate("onboarding_graph")
                        },
                        onDismissClick = {
                            showError = false
                            rootNavController.navigate("onboarding_graph")
                        }
                    )
                }

                SelectFixedView(
                    navController = rootNavController
                ) { fixedOption ->
                    onboardingVM.tempFixedExpensesOption = fixedOption
                    onboardingVM.saveOnboardingData(
                        goal = onboardingVM.tempGoal,
                        initialBalance = onboardingVM.tempInitialBalance.toDoubleOrNull() ?: 0.0,
                        favoriteCategories = onboardingVM.tempCategories,
                        fixedExpensesOption = onboardingVM.tempFixedExpensesOption,
                        onSuccess = {
                            loginVM.setOnboardingCompleted {
                                rootNavController.navigate("main_graph") {
                                    popUpTo("onboarding_graph") { inclusive = true }
                                }
                            }
                        },
                        onError = { error ->
                            errorMessage = error
                            showError = true
                        }
                    )
                }
            }
        }

        navigation(
            startDestination = MAIN_NAV_ROUTE,
            route = "main_graph"
        ) {

            composable(MAIN_NAV_ROUTE) {
                val homeVM = hiltViewModel<HomeViewModel>()
                val splitVM = hiltViewModel<SplitAccountViewModel>()
                MainNavManager(loginVM, rootNavController, homeVM, splitVM) }
        }

        navigation(
            startDestination = SPLASH_ROUTE,
            route = "splash_graph"
        ) {
            composable(SPLASH_ROUTE) {
                SplashView(
                    navController = rootNavController,
                )
            }
        }
    }
}


