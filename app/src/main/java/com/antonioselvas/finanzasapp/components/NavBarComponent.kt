package com.antonioselvas.finanzasapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.antonioselvas.finanzasapp.models.BottomNavItem
import com.antonioselvas.finanzasapp.presentation.views.homeViews.HOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SPLIT_ACCOUNT_ROUTE
import com.antonioselvas.finanzasapp.presentation.views.statisticsViews.STATISTICS_ROUTE
import primaryColor
import secondaryText


@Composable
fun BottomNavigationBar(navController: NavController){
    val navItems = listOf(
        BottomNavItem(
            route = HOME_ROUTE,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            description = "Home",
            label = "Inicio"
        ),
        BottomNavItem(
            route = SPLIT_ACCOUNT_ROUTE,
            selectedIcon = Icons.Filled.Share,
            unselectedIcon = Icons.Outlined.Share,
            description = "SplitAccount",
            label = "Compartidas"
        ),
        BottomNavItem(
            route = HOME_ROUTE,
            selectedIcon = Icons.Filled.Discount,
            unselectedIcon = Icons.Outlined.Discount,
            description = "FixedExpenses",
            label = "Fijos"
        ),
        BottomNavItem(
            route = STATISTICS_ROUTE,
            selectedIcon = Icons.Filled.QueryStats,
            unselectedIcon = Icons.Outlined.QueryStats,
            description = "Statistics",
            label = "Estadísticas"
        )
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color.White
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (index == selectedItemIndex){
                            primaryColor
                        } else secondaryText
                    )
                },
                icon = {
                    BadgedBox(
                        badge = {
                        }
                    ) {
                        Icon(
                            tint =  if (index == selectedItemIndex){
                                primaryColor
                            } else secondaryText,
                            imageVector = if (index == selectedItemIndex){
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.description
                        )
                    }
                }
            )
        }

    }
}