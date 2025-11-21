package com.antonioselvas.finanzasapp.components

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import primaryColor
import textField


@Composable
fun TextFieldComponent(
    label: String,
    placeHolder: String,
    value: String,
    onValue: (String) -> Unit
){

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValue,
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeHolder)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = textField
        ),
        maxLines = 2
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