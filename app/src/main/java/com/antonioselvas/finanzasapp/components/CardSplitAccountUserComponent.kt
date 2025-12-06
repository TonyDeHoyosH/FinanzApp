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
fun CardSplitAccountAddUser(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    amountColor: Color = Color.Red,
    labelColor: Color = primaryText,
    createdUser: (SplitAccount) -> Unit,
    users: MutableList<SplitAccount>,
    isEquitable: Boolean = false,
    subTotal: Double = 0.0,
    total: Double = 0.0
) {
    var showAlert by remember { mutableStateOf(false) }
    val equitableAmount = if (isEquitable && users.size > 0) {
        val remainingUsersCount = users.size + 1
        total / remainingUsersCount
    } else if (isEquitable) {
        total
    } else {
        0.0
    }


    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        var name by remember { mutableStateOf("") }
        var amount by remember {
            mutableStateOf(if (isEquitable) String.format("%.2f", equitableAmount) else "0.0")
        }
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
                .height(if (isEquitable) 240.dp else 280.dp)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                "Agregando un amigo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
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
                            text ="Nombre:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = secondaryText )
                    },
                    value = name,
                    onValueChange = { name = it },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),

                    )

                if (!isEquitable) {
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
                } else {

                    Text(
                        text = "Monto Asignado: $${String.format("%.2f", equitableAmount)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        textAlign = TextAlign.Center
                    )
                }
                val currentAmountDouble = amount.toDoubleOrNull() ?: 0.0

                val isAmountValid = if (isEquitable) {
                    true
                } else {

                    currentAmountDouble > 0.0 && (subTotal + currentAmountDouble) <= total
                }

                val isFormValid = name.isNotEmpty() && isAmountValid

                if (!isEquitable && (subTotal + currentAmountDouble) > total) {
                    showAlert = true
                    Text(
                        text = "⚠️ El monto excede el gasto restante ($${String.format("%.2f", total - subTotal)})",
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
                        val finalAmount = if (isEquitable) equitableAmount.toFloat() else currentAmountDouble.toFloat()

                        val newUser = SplitAccount(
                            id = (users.size + 1).toString(),
                            name = name,
                            amount = finalAmount,
                            paidAmount = 0f,
                            paid = false,
                            deleted = false
                        )
                        createdUser(newUser)
                        onDismissRequest()
                    },
                    enabled = isFormValid
                ) {
                    Text(
                        "Agregar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

    }
}


//    @Preview(showBackground = true)
//    @Composable
//    fun PreviewCardSplit() {
//        Scaffold {
//            FinancesAppTheme {
//                Box(
//                    modifier = Modifier
//                        .padding(it)
//                        .fillMaxSize()
//                ) {
//
//
//                }
//
//            }
//        }
//    }
//}