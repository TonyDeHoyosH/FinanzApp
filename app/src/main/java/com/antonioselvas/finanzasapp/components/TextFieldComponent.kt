package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import red
import secondaryText


@Composable
fun TextFieldComponent(
    label: String,
    placeHolder: String,
    value: String,
    onValue: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null
){
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValue,
            label = {
                Text(
                    text = label,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = secondaryText
                )
            },
            placeholder = {
                Text(
                    placeHolder,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            isError = isError
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTextField(){
//    FinancesAppTheme {
//        Scaffold {
//            Column(
//                modifier = Modifier.padding(it).fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//
//            }
//            TextFieldComponent(
//                label = "Descripcion",
//                placeHolder = "Ej: Café con amigos",
//                value = "",
//                onValue = {},
//
//            )
//        }
//
//    }
