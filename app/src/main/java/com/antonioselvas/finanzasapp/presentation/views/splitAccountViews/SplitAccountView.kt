package com.antonioselvas.finanzasapp.presentation.views.splitAccountViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import com.antonioselvas.finanzasapp.components.SelectCardComponent
import com.antonioselvas.finanzasapp.domain.models.CategoryMap
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitAccountViewModel
import gradientYellow
import primaryColor
import primaryText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val SPLIT_ACCOUNT_ROUTE = "SplitAccount"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitAccountView(navController: NavHostController, splitVM: SplitAccountViewModel) {
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
                onClick = {
                    navController.navigate(NEW_SPLIT_ACCOUNT_ROUTE)
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
        SplitAccountContent(it, navController, splitVM)
    }
}

@Composable
fun SplitAccountContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    splitVM: SplitAccountViewModel
){
    val uiState by splitVM.uiState.collectAsState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (splitVM.uiState.collectAsState().value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        if (splitVM.uiState.collectAsState().value.error != null) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Error: ${splitVM.uiState.collectAsState().value.error}")
                Button(onClick = { splitVM.loadSplitAccountData() }) {
                    Text("Reintentar")
                }
            }
            return
        }


            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.splitAccounts) { it ->
                    val categoryAppearance = CategoryMap[it.category] ?: CategoryMap["Otros"]!!
                    SelectCardComponent(
                        label = it.description,
                        imageVector = categoryAppearance.icon,
                        iconColor = categoryAppearance.iconColor,
                        bgColor = categoryAppearance.backgroundColor,
                        selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        onClick = {navController.navigate("SplitAccountDetail/${it.id}")},
                        date = convertMillisToDate(it.date),
                        amount = it.amount.toString(),
                        amountColor = Color(0xFFE53935),
                        labelColor = primaryText,
                        numPersons = it.usersNumber
                    )
                }
            }

    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
