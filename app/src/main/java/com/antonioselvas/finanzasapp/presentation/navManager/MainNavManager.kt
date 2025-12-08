package com.antonioselvas.finanzasapp.presentation.navManager

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antonioselvas.finanzasapp.components.BottomNavigationBar
import com.antonioselvas.finanzasapp.presentation.viewModels.AuthViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.FixedExpenseViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.HomeViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitAccountViewModel
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.FIXED_EXPENSES_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.FixedExpenseView
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.NEW_FIXED_EXPENSE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews.NewFixedExpenseView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HomeView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.NEW_EXPENSE_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.NewExpenseView
import com.antonioselvas.finanzasapp.presentation.views.homeViews.USER_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.homeViews.UserAccountView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.NEW_SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.NewSplitAccountView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitAccountDetailsView
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitAccountView


const val MAIN_NAV_ROUTE = "main_nav"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavManager(
    loginVM: AuthViewModel,
    rootNavController: NavHostController,
    homeVM: HomeViewModel,
    splitVM: SplitAccountViewModel,
    fixedVM: FixedExpenseViewModel
) {
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

            composable(HOME_ROUTE) { HomeView(navController, homeVM) }

            composable(USER_ACCOUNT_ROUTE) { UserAccountView(rootNavController, loginVM, navController, homeVM) }

            composable(NEW_EXPENSE_ROUTE) { NewExpenseView(navController, homeVM) }


            composable(
                route = "SplitAccountDetail/{id}",
                arguments = listOf(
                    navArgument( name = "id"){
                        type = NavType.StringType
                    }
                )
            ) { backstackEntry ->
                val id = backstackEntry.arguments?.getString("id")
                SplitAccountDetailsView(navController, splitVM, id)}

            composable(NEW_SPLIT_ACCOUNT_ROUTE) { NewSplitAccountView(navController, splitVM) }

            composable(SPLIT_ACCOUNT_ROUTE) { SplitAccountView(navController, splitVM) }

            composable(FIXED_EXPENSES_ROUTE) { FixedExpenseView(navController, fixedVM) }

            composable(NEW_FIXED_EXPENSE_ROUTE) { NewFixedExpenseView(navController, fixedVM) }
        }
    }

}