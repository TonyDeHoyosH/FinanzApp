package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bgBlue
import bgGreen
import bgRed
import bgYellow
import com.antonioselvas.finanzasapp.components.onboarding.SelectCardComponent
import com.antonioselvas.finanzasapp.components.onboarding.Stepper
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import green
import primaryColor
import primaryText
import red
import yellow

data class Option(
    val label: String,
    val icon: ImageVector,
    val colorType: String
)

const val SELECT_FIXED_ROUTE = "SelectFixed"


@Composable
fun SelectFixedView(navController: NavHostController) {
    var selectedOption by remember { mutableStateOf("") }
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
                    currentStep = 3
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "¿Tienes gastos\n" +
                            "recurrentes?",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = primaryText
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .width(352.dp)
                    ,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                    ),
                    enabled = selectedOption.isNotEmpty(),
                    onClick = {}
                ) {
                    Text("Finalizar")
                }
            }

        }
    ) {
        SelectFixedContent(it, selectedOption = selectedOption,
            onOptionSelected = { goal -> selectedOption = goal })
    }
}
@Composable
fun SelectFixedContent(
    paddingValues: PaddingValues,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
){
    val options = remember {
        listOf(
            Option("Sí, tengo gastos mensuales", Icons.Outlined.CreditCard, "green"),
            Option("No, solo gastos ocasionales", Icons.Outlined.Block, "red"),
            Option("Dividir cuentas", Icons.Outlined.Info, "yellow"),
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(options) { option ->
            // Determinar colores según el tipo
            val iconColor = when (option.colorType) {
                "yellow" -> yellow
                "blue" -> primaryColor
                "red" -> red
                "green" -> green
                else -> green
            }

            val  bgColor = when (option.colorType) {
                "yellow" -> bgYellow
                "blue" -> bgBlue
                "red" -> bgRed
                "green" -> bgGreen
                else -> bgGreen
            }

            SelectCardComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(77.dp),
                label = option.label,
                selected = selectedOption == option.label,
                imageVector = option.icon,
                iconColor = iconColor,
                bgColor = bgColor,
                onClick = { onOptionSelected(option.label) }
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewFixed(){
//    FinancesAppTheme{
//        SelectFixedView(navController)
//    }
//}
