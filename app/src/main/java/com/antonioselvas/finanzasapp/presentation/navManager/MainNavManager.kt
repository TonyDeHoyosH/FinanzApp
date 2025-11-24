package com.antonioselvas.finanzasapp.presentation.navManager

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitAccountView


const val MAIN_NAV_ROUTE = "MainNav"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun MainNavManager(){
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
            composable(
                HOME_ROUTE
            ) {
                HomeView(navController)
            }

            composable(NEW_EXPENSE_ROUTE) { NewExpenseView(navController) }

            composable(FIXED_EXPENSES_ROUTE) { FixedExpenseView() }

            composable(
                SPLIT_ACCOUNT_ROUTE
            ) {
                SplitAccountView()
            }
        }
    }

}