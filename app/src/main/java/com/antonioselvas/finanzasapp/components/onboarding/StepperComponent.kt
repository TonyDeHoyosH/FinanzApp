package com.antonioselvas.finanzasapp.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme


@Composable
fun Stepper(
    totalSteps: Int,
    currentStep: Int
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        for (i in 0 until totalSteps){
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(28.dp)
                    .background(
                        if (i <= currentStep) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
            if (i != 3){
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStepper(){
    FinancesAppTheme {
        Stepper(
            4,
            2
        )
    }
}