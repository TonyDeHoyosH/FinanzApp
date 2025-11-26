package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bgBlue
import com.antonioselvas.finanzasapp.components.onboarding.NextButtonComponent
import com.antonioselvas.finanzasapp.components.onboarding.Stepper
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import primaryColor
import primaryText
import secondaryText

const val BALANCE_ROUTE = "Balance"

@Composable
fun AddBalanceView(navController: NavHostController, onBalanceChange: (String) -> Unit) {
    var balance by remember { mutableStateOf("") }
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
                            color = bgBlue,
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
                        tint = primaryColor,
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "¿Cuánto dinero tienes\n" +
                            "disponible actualmente?",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = primaryText
                )

            }
        },
        bottomBar = {
            NextButtonComponent(
                {
                    onBalanceChange(balance)
                },
                "Siguiente",
                enable = balance.isNotEmpty()
            )

        }
    ) {
        AddBalanceContent(it, balance = balance,
            onBalanceChange = { b -> balance = b })
    }
}


@Composable
fun AddBalanceContent(
    paddingValues: PaddingValues,
    balance: String,
    onBalanceChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))


        BasicTextField(
            value = balance,
            onValueChange = { newValue ->

                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                    onBalanceChange(newValue)
                }
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 56.sp,
                color = if (balance.isEmpty()) secondaryText else primaryColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (balance.isEmpty()) {
                        Text(
                            text = "$0.00",
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Bold,
                            color = secondaryText,
                            textAlign = TextAlign.Center
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "No te preocupes, puedes cambiarlo\ndespués",
            textAlign = TextAlign.Center,
            color = secondaryText,
            fontSize = 14.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAddBalance(){
//    FinancesAppTheme{
//        AddBalanceView(navController)
//    }
//}