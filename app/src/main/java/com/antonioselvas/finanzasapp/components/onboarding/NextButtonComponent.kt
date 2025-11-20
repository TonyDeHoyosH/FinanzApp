package com.antonioselvas.finanzasapp.components.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import primaryColor


@Composable
fun NextButtonComponent(
    navController: () -> Unit,
    label: String
){
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
                .height(44.dp)
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
            ),
            onClick = {
                navController()
            }
        ) {
            Text(label)
        }
    }
}