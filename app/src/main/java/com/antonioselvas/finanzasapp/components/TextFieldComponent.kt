package com.antonioselvas.finanzasapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import primaryColor
import secondaryText
import textField


@Composable
fun TextFieldComponent(
    label: String,
    placeHolder: String,
    value: String,
    onValue: (String) -> Unit
){

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValue,
        label = {
            Text(
                text =label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = secondaryText )
        },
        placeholder = {
            placeHolder
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.White
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTextField(){
    FinancesAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.padding(it).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

            }
            TextFieldComponent(
                label = "Descripcion",
                placeHolder = "Ej: Café con amigos",
                value = "",
                onValue = {}
            )
        }
       
    }
}