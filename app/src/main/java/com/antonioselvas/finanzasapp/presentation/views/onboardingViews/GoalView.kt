package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.components.onboarding.SelectCardComponent
import com.antonioselvas.finanzasapp.ui.theme.ExtendedTheme
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans

const val GOAL_ROUTE = "Goal"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalView(){
    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(vertical = 60.dp),
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "¿Cuál es tu objetivo\n" +
                                "principal?",
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                    )
                },
            )
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
                    onClick = {}
                ) {
                    Text("Siguiente")
                }
            }

        }
    ) {
        GoalContent(it)
    }
}

@Composable
fun GoalContent(paddingValues: PaddingValues){
    var selected by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            )
    ) {
        SelectCardComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(77.dp),
            label = "Ahorrar dinero",
            selected = selected,
            imageVector = Icons.Outlined.Savings,
            iconColor = ExtendedTheme.colors.yellow,
            bgColor = ExtendedTheme.colors.iconsBgYellow,
            onClick = {
                selected = !selected
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGoal(){
    FinancesAppTheme{
        GoalView()
    }
}