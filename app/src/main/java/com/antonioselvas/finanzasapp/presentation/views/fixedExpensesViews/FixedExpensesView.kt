package com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.CardFixedExpense
import com.antonioselvas.finanzasapp.domain.models.CategoryMap
import com.antonioselvas.finanzasapp.domain.models.FixedExpense
import com.antonioselvas.finanzasapp.presentation.viewModels.FixedExpenseViewModel
import gradientRed
import primaryColor
import primaryText


const val FIXED_EXPENSES_ROUTE = "fixed_expense"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedExpenseView(navController: NavHostController, fixedVM: FixedExpenseViewModel) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(gradientRed, Color.White),
                    startY = 0.0f,
                    endY = 1500f
                )
            ),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Gastos Fijos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = {
                    navController.navigate(NEW_FIXED_EXPENSE_ROUTE)
                },
                containerColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddBalance",
                    tint = primaryColor
                )
            }
        }
    ) {
        FixedExpenseContent(it, fixedVM)
    }

}

@Composable
fun FixedExpenseContent(paddingValues: PaddingValues, fixedVM: FixedExpenseViewModel) {

    val fixedExpenses by fixedVM.fixedExpenses.collectAsState()
    val monthlyTotal by fixedVM.monthlyTotal.collectAsState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 60.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .width(234.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gasto Total Mensual:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = primaryText
                    )
                    Text(
                        text = "$${"%.2f".format(monthlyTotal)}",
                        fontSize = 46.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Gastos fijos",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryText
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(fixedExpenses){expense ->
                    FixedExpenseItem(expense)
                }
            }
        }
    }
}

@Composable
private fun FixedExpenseItem(expense: FixedExpense) {
    val categoryAppearance = CategoryMap[expense.category] ?: CategoryMap["Otros"]!!
    CardFixedExpense(
        label = expense.description,
        imageVector =  categoryAppearance.icon,
        iconColor =  categoryAppearance.iconColor,
        bgColor =  categoryAppearance.backgroundColor,
        onClick = { /* Navegar a detalles/edición */ },
        type = expense.frequency,
        day = if (expense.frequency == "Mensual") expense.chargeDay.toString() else "-",
        amount = "-$${"%.2f".format(expense.amount)}",
        amountColor = Color(0xFFE53935),
        labelColor = Color(0xFF2C2C2C),
        alert = if (expense.nextChargeDate <= System.currentTimeMillis()) "Vencido" else "Próximo",
        colorAlert = if (expense.nextChargeDate <= System.currentTimeMillis()) 1 else 0
    )
}