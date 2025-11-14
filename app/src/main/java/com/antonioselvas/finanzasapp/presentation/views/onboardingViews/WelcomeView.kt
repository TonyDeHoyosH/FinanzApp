package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans

const val ONBOARDING_ROUTE = "OnBoarding"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeView(navController: NavController){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(vertical = 40.dp),
                title = {
                    Text(
                        text = "FinanzApp",
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
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
                        .height(44.dp)
                    ,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  MaterialTheme.colorScheme.primary,
                    ),
                    onClick = {
                        navController.navigate(GOAL_ROUTE)
                    }
                ) {
                    Text("Siguiente")
                }
            }

        }
    ) {
        WelcomeContent(it)
    }
}

@Composable
fun WelcomeContent(paddingValues: PaddingValues){
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = "Welcome Img")
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Text(
            text = "¡Bienvenido a FinanzApp!",
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Text(
            text = """Controla tus finanzas personales
                |de forma simple y segura.""".trimMargin(),
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outlineVariant
        )



    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewWelcome(){
//    FinancesAppTheme{
//    WelcomeView()
//    }
//}