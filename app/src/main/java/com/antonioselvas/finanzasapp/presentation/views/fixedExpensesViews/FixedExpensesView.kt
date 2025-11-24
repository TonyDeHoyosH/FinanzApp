package com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.components.CardFixedExpense
import com.antonioselvas.finanzasapp.components.SelectCardComponent
import gradientBlue
import gradientRed
import primaryColor
import primaryText


const val FIXED_EXPENSES_ROUTE = "FixedExpense"

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedExpenseView(){
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
                onClick = {},
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
        FixedExpenseContent(it)
    }
}

@Composable
fun FixedExpenseContent(paddingValues: PaddingValues){
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
        ){
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
                    text = "$50.00",
                    fontSize = 46.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryText
                )
            }
        }
        }

        Column(
            modifier = Modifier.fillMaxSize()
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
                // Gasto 1: MotoTaxi
                item {
                    CardFixedExpense(
                        label = "MotoTaxi",
                        imageVector = Icons.Outlined.LocalTaxi,
                        iconColor = Color(0xFF2C2C2C),
                        bgColor = Color(0xFFF5F5F5),
                        onClick = {},
                        type = "Mensual",
                        day = "15",
                        amount = "-$10.00",
                        amountColor = Color(0xFFE53935),
                        labelColor = Color(0xFF2C2C2C),
                        alert = "Pediente",
                        colorAlert = 1
                    )
                }

                item {
                    CardFixedExpense(
                        label = "MotoTaxi",
                        imageVector = Icons.Outlined.LocalTaxi,
                        iconColor = Color(0xFF2C2C2C),
                        bgColor = Color(0xFFF5F5F5),
                        onClick = {},
                        type = "Mensual",
                        day = "15",
                        amount = "-$10.00",
                        amountColor = Color(0xFFE53935),
                        labelColor = Color(0xFF2C2C2C),
                        alert = "Pediente",
                        colorAlert = 1
                    )
                }
            }
        }
    }
}