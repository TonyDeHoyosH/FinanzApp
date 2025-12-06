package com.antonioselvas.finanzasapp.presentation.views.homeViews

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bgBlue
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.components.AddBalanceModal
import com.antonioselvas.finanzasapp.components.SelectCardComponent
import com.antonioselvas.finanzasapp.domain.models.CategoryMap
import com.antonioselvas.finanzasapp.presentation.viewModels.HomeViewModel
import gradientBlue
import primaryColor
import primaryText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val HOME_ROUTE = "Home"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavHostController, homeVM: HomeViewModel) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(gradientBlue, Color.White),
                    startY = 0.0f,
                    endY = 1500f
                )
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier,
                title = {
                    Text(
                        text = "Hola, ${homeVM.userInfo.collectAsState().value.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = {
                            navController.navigate(USER_ACCOUNT_ROUTE)
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(120.dp)
                            ,
                            painter = painterResource(R.drawable.account_circle),
                            contentDescription = "Account"
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            ),
                        onClick = {}
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(28.dp)
                            ,
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            )
        }
    ) {
        HomeContent(it, navController, homeVM)
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    homeVM: HomeViewModel
){
    val userInfo by homeVM.userInfo.collectAsState()
    var showAddBalance by remember { mutableStateOf(false) }
    if (homeVM.uiState.collectAsState().value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (homeVM.uiState.collectAsState().value.error != null) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Error: ${homeVM.uiState.collectAsState().value.error}")
            Button(onClick = { homeVM.loadHomeData() }) {
                Text("Reintentar")
            }
        }
        return
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(top = 60.dp)
                .height(238.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .width(244.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Balance Total:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = primaryText
                    )
                    Text(
                        text = String.format("$%.2f", homeVM.uiState.collectAsState().value.currentBalance),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                IconButton(
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = CircleShape,
                            clip = false
                        ),
                    onClick = {
                        showAddBalance = true
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "AddBalance"
                    )
                }

            }
            if (showAddBalance){
                AddBalanceModal(
                    onDismissRequest = { showAddBalance = false},
                    homeVM
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            SelectCardComponent(
                label = "Añadir Gasto",
                imageVector = Icons.Outlined.CreditCard,
                iconColor = primaryColor,
                bgColor = bgBlue,
                selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {
                    navController.navigate(NEW_EXPENSE_ROUTE)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Gastos recientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryText
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(userInfo.lastExpens){ it ->
                    val categoryAppearance = CategoryMap[it.category] ?: CategoryMap["Otros"]!!

                    val numPersons = if (it.divisionForm != null) {
                        it.users.size
                    } else {
                        null
                    }
                    if (it.typeTransaction == "expense"){
                    SelectCardComponent(
                        label = it.description,
                        imageVector = categoryAppearance.icon,
                        selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        onClick = {  },
                        date = convertMillisToDate(it.date),
                        amount = it.amount.toString(),
                        iconColor = categoryAppearance.iconColor,
                        bgColor = categoryAppearance.backgroundColor,
                        amountColor = Color(0xFFE53935),
                        numPersons = numPersons
                    )
                    }
                }
            }
        }

    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewHome(){
//    FinancesAppTheme {
//        HomeView(navController)
//    }
//}