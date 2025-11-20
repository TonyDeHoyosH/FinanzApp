package com.antonioselvas.finanzasapp.presentation.views.homeViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bgBlue
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.components.SelectCardComponent
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import gradientBlue
import primaryColor
import primaryText


const val HOME_ROUTE = "HomeView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(){
    Scaffold(
        containerColor = Color.Transparent, // ← CLAVE
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(gradientBlue, Color.White),
                    startY = 0.0f,
                    endY = 1500f
                )
            ).padding(top = 10.dp),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.padding(horizontal = 12.dp).clip(CircleShape),
                title = {
                    Text(
                        text = "Hola, Antonio",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
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
        HomeContent(it)
    }
}


@Composable
fun HomeContent(paddingValues: PaddingValues){
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
                    .width(234.dp),
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
                        text = "$50.00",
                        fontSize = 46.sp,
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
                    onClick = {},
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
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            SelectCardComponent(
                label = "Añadir Gasto",
                imageVector = Icons.Outlined.CreditCard,
                iconColor = primaryColor,
                bgColor = bgBlue,
                selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {}
            )
        }
        Column(
            modifier = Modifier.fillMaxSize()
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Gasto 1: MotoTaxi
                item {
                    SelectCardComponent(
                        label = "MotoTaxi",
                        imageVector = Icons.Outlined.LocalTaxi,
                        iconColor = Color(0xFF2C2C2C),
                        bgColor = Color(0xFFF5F5F5),
                        selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        onClick = {},
                        date = "15 jul",
                        amount = "-$10.00",
                        amountColor = Color(0xFFE53935),
                        labelColor = Color(0xFF2C2C2C)
                    )
                }

                // Gasto 2: Comida - Restaurante
                item {
                    SelectCardComponent(
                        label = "Restaurante",
                        imageVector = Icons.Outlined.Restaurant,
                        iconColor = Color(0xFFFF6F00),
                        bgColor = Color(0xFFFFE0B2),
                        selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        onClick = {},
                        date = "14 jul",
                        amount = "-$25.50",
                        amountColor = Color(0xFFE53935),
                        labelColor = Color(0xFF2C2C2C)
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome(){
    FinancesAppTheme {
        HomeView()
    }
}