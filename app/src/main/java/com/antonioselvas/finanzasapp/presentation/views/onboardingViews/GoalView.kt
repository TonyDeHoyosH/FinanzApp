package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bgBlue
import bgGreen
import bgRed
import bgYellow
import com.antonioselvas.finanzasapp.components.onboarding.NextButtonComponent
import com.antonioselvas.finanzasapp.components.onboarding.SelectCardComponent
import com.antonioselvas.finanzasapp.components.onboarding.Stepper
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import green
import primaryColor
import red
import yellow

const val GOAL_ROUTE = "Goal"

// Data class para los objetivos
data class Goal(
    val label: String,
    val icon: ImageVector,
    val colorType: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalView(navController: NavHostController, onGoalSelected: (String) -> Unit) {
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
                    currentStep = 0
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "¿Cuál es tu objetivo\nprincipal?",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        },
        bottomBar = {
            NextButtonComponent(
                {
                    onGoalSelected(selectedGoal)
                },
                "Siguiente",
                enable = selectedGoal.isNotEmpty()
            )

        }
    ) {
        GoalContent(it, selectedGoal = selectedGoal,
            onGoalSelected = { goal -> selectedGoal = goal })
    }
}

@Composable
fun GoalContent(
    paddingValues: PaddingValues,
    selectedGoal: String,
    onGoalSelected: (String) -> Unit
) {
    val goals = remember {
        listOf(
            Goal("Ahorrar dinero", Icons.Outlined.Savings, "yellow"),
            Goal("Controlar mis gastos", Icons.Outlined.Receipt, "blue"),
            Goal("Dividir cuentas", Icons.Outlined.Group, "red"),
            Goal("Pagar gastos fijos", Icons.Outlined.Schedule, "green"),
            Goal("Ver estadísticas", Icons.Outlined.BarChart, "yellow"),
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(goals) { goal ->
            // Determinar colores según el tipo
            val iconColor = when (goal.colorType) {
                "yellow" -> yellow
                "blue" -> primaryColor
                "red" -> red
                "green" -> green
                else -> green
            }

            val  bgColor = when (goal.colorType) {
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
                label = goal.label,
                selected = selectedGoal == goal.label,
                imageVector = goal.icon,
                iconColor = iconColor,
                bgColor = bgColor,
                onClick = { onGoalSelected(goal.label) }
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewGoal(){
//    FinancesAppTheme{
//        GoalView()
//    }
//}