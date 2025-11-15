package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.components.onboarding.Stepper
import com.antonioselvas.finanzasapp.ui.theme.ExtendedTheme
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans

const val BALANCE_ROUTE = "Balance"

@Composable
fun AddBalanceView(){
    var selectedGoal by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Stepper(
                    totalSteps = 4,
                    currentStep = 1
                )
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier= Modifier
                        .padding(10.dp)
                        .width(84.dp)
                        .height(84.dp)
                        .background(
                            color = ExtendedTheme.colors.iconsBgBlue.colorContainer,
                            shape = CircleShape
                        )
                    ,
                    contentAlignment = Alignment.Center,


                    ){
                    Icon(
                        modifier = Modifier
                            .size(44.dp)
                        ,
                        imageVector = Icons.Outlined.Savings,
                        contentDescription = "",
                        tint = ExtendedTheme.colors.blue.color,
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "¿Cuánto dinero tienes\n" +
                            "disponible actiualmente?",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 80.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .width(352.dp)
                    ,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    enabled = selectedGoal.isNotEmpty(),
                    onClick = {}
                ) {
                    Text("Siguiente")
                }
            }

        }
    ) {
        AddBalanceContent(it)
    }
}


@Composable
fun AddBalanceContent(paddingValues: PaddingValues) {
    var balance by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            ),
            value = balance,
            onValueChange = { balance = it },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.LightGray,
                focusedTextColor = ExtendedTheme.colors.blue.color,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(
                "$0.00",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            ) },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "No te preocupes, puedes cambiarlo\ndespues",
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddBalance(){
    FinancesAppTheme{
        AddBalanceView()
    }
}