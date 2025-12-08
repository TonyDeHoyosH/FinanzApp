package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import primaryColor
import primaryText
import secondaryText
import yellow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSplitAccountEditAmount(
    onDismissRequest: () -> Unit,
    userToEdit: SplitAccount,
    onSaveEdit: (SplitAccount) -> Unit,
    currentSubTotal: Double,
    totalExpense: Double,
) {
    var showAlert by remember { mutableStateOf(false) }

    var amount by remember {
        mutableStateOf(String.format("%.2f", userToEdit.amount))
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 6.dp,
                    shape = CircleShape,
                    clip = false
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                .height(280.dp)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                "Editar Monto de ${userToEdit.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    label = {
                        Text(
                            text ="Monto:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = secondaryText )
                    },
                    value = amount,
                    onValueChange = { newAmount ->
                        if (newAmount.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                            amount = newAmount
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                val newAmountDouble = amount.toDoubleOrNull() ?: 0.0


                val subTotalWithoutCurrent = currentSubTotal - userToEdit.amount
                val newSubTotal = subTotalWithoutCurrent + newAmountDouble

                val isAmountValid = newAmountDouble > 0.0 && newSubTotal <= totalExpense

                val isFormValid = isAmountValid

                if (!isAmountValid && newSubTotal > totalExpense) {
                    showAlert = true
                    Text(
                        text = "⚠️ El monto excede el gasto restante ($${String.format("%.2f", totalExpense - subTotalWithoutCurrent)})",
                        color = yellow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        val updatedUser = userToEdit.copy(
                            amount = newAmountDouble
                        )
                        onSaveEdit(updatedUser)
                        onDismissRequest()
                    },
                    enabled = isFormValid
                ) {
                    Text(
                        "Guardar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

    }
}