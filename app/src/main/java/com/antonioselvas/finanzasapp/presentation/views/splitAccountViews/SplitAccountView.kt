package com.antonioselvas.finanzasapp.presentation.views.splitAccountViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.SelectCardComponent
import gradientYellow
import primaryColor
import primaryText


const val SPLIT_ACCOUNT_ROUTE = "SplitAccount"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitAccountView(navController: NavHostController) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(gradientYellow, Color.White),
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
                        text = "Gastos Compartidos",
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
        SplitAccountContent(it, navController)
    }
}

@Composable
fun SplitAccountContent(paddingValues: PaddingValues, navController: NavHostController){
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SelectCardComponent(
                        label = "Restaurante",
                        imageVector = Icons.Outlined.Restaurant,
                        iconColor = Color(0xFFFF6F00),
                        bgColor = Color(0xFFFFE0B2),
                        selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        onClick = {navController.navigate(SPLIT_ACCOUNT_DETAIL_ROUTE)},
                        date = "14 jul",
                        amount = "-$25.50",
                        amountColor = Color(0xFFE53935),
                        labelColor = Color(0xFF2C2C2C),
                        numPersons = 4
                    )
                }
            }

    }
}
