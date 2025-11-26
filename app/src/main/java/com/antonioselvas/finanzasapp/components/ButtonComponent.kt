package com.antonioselvas.finanzasapp.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import primaryColor


@Composable
fun ButtonComponent(
    navController: () -> Unit,
    label: String,
    enable: Boolean
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
                .fillMaxWidth()
                .height(44.dp)
                ,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
            ),
            onClick = {
                navController()
            },
            enabled = enable
        ) {
            Text(label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}