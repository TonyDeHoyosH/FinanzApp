package com.antonioselvas.finanzasapp.presentation.navManager

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonioselvas.finanzasapp.components.BottomNavigationBar
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.FIXED_EXPENSES_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.FixedExpenseView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HomeView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.NEW_EXPENSE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.NewExpenseView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.USER_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.UserAccountView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.NEW_SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.NewSplitAccountView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SPLIT_ACCOUNT_DETAIL_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitAccountDetailsView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitAccountView
import com.antonioselvas.finanzasapp.viewModels.AuthViewModel


const val MAIN_NAV_ROUTE = "MainNav"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavManager(loginVM: AuthViewModel, rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE
        ){

            composable(HOME_ROUTE) { HomeView(navController) }

            composable(USER_ACCOUNT_ROUTE) { UserAccountView(rootNavController, loginVM, navController) }

            composable(NEW_EXPENSE_ROUTE) { NewExpenseView(navController) }

            composable(FIXED_EXPENSES_ROUTE) { FixedExpenseView() }

            composable(SPLIT_ACCOUNT_DETAIL_ROUTE) { SplitAccountDetailsView(navController) }

            composable(NEW_SPLIT_ACCOUNT_ROUTE) { NewSplitAccountView() }

            composable(SPLIT_ACCOUNT_ROUTE) { SplitAccountView(navController) }
        }
    }

}