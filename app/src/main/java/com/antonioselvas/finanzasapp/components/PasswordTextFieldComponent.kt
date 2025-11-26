package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import secondaryText
import primaryColor

@Composable
fun PasswordTextFieldComponent(
    label: String,
    placeHolder: String,
    value: String,
    onValue: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValue,
        label = {
            Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Normal, color = secondaryText)
        },
        placeholder = {
            Text(text = placeHolder)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
            Icon(
                imageVector = image,
                contentDescription = description,
                modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                tint = primaryColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.White
        )
    )
}
