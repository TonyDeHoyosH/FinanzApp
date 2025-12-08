package com.antonioselvas.finanzasapp.presentation.views.fixedExpensesViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.DatePickerFieldToModal
import com.antonioselvas.finanzasapp.components.DropDownComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.presentation.viewModels.FixedExpenseViewModel
import primaryColor
import primaryText
import secondaryText


const val NEW_FIXED_EXPENSE_ROUTE = "new_fixed_expense"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFixedExpenseView(navController: NavController, fixedVM: FixedExpenseViewModel) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Nuevo Gasto Fijo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        NewFixedExpenseContent(
            it,
            navController,
            fixedVM
        )
    }
}

@Composable
fun NewFixedExpenseContent(
    paddingValues: PaddingValues,
    navController: NavController,
    fixedVM: FixedExpenseViewModel,
) {
    var expense by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var chargeDay by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    var chargeDayError by remember { mutableStateOf<String?>(null) }

    var showAddFriend by remember { mutableStateOf(false) }

    val isButtonEnabled =
        remember(expense, description, category, frequency, chargeDay, selectedDate) {
            val amountIsValid = expense.toDoubleOrNull() != null && expense.toDouble() > 0
            val chargeDayIsValid: Boolean

            if (frequency == "Diario") {
                chargeDayIsValid = true
            } else {
                val day = chargeDay.toIntOrNull()
                chargeDayIsValid = day != null && day in 1..31
            }

            amountIsValid &&
                    description.isNotBlank() &&
                    category.isNotBlank() &&
                    frequency.isNotBlank() &&
                    type.isNotBlank() &&
                    selectedDate != null &&
                    chargeDayIsValid
        }

    val categories: MutableList<String> = remember {
        mutableListOf(
            "Alimentación",
            "Transporte",
            "Hogar",
            "Servicio publico",
            "Ropa",
            "Salud",
            "Educación",
            "Entretenimiento",
            "Mascotas",
            "Otros"
        )
    }

    val types: MutableList<String> = remember {
        mutableListOf(
            "Imprevisto",
            "Ahorro",
            "Deuda",
            "Gusto personal",
            "Regalo"
        )
    }

    val frequencies: MutableList<String> = remember {
        mutableListOf("Mensual", "Semanal", "Diario", "Anual")
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.height(150.dp),
            verticalArrangement = Arrangement.Center
        ) {
            BasicTextField(
                value = expense,
                onValueChange = { newValue ->

                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        expense = newValue
                    }
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 56.sp,
                    color = if (expense.isEmpty()) secondaryText else primaryColor
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (expense.isEmpty()) {
                            Text(
                                text = "$0.00",
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold,
                                color = secondaryText,
                                textAlign = TextAlign.Center
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextFieldComponent(
                label = "Descripcion",
                placeHolder = "Ej: Café con amigos",
                value = description,
                onValue = {
                    description = it
                }
            )

            DropDownComponent(
                label = "Categoria",
                listOfCategories = categories,
                selectedText = category,
                onSelectedText = { category = it }
            )

            DropDownComponent(
                label = "Frecuencia",
                listOfCategories = frequencies,
                selectedText = frequency,
                onSelectedText = {
                    frequency = it
                    chargeDay = ""
                    chargeDayError = null
                }
            )

            DropDownComponent(
                label = "Tipo",
                listOfCategories = types,
                selectedText = type,
                onSelectedText = { type = it }
            )
            if (frequency == "Mensual") {
                TextFieldComponent(
                    label = "Día de Cobro (1-31)",
                    placeHolder = "Ej: 5 (para el día 5 de cada mes)",
                    value = chargeDay,
                    onValue = { newValue ->
                        chargeDayError = null // Resetear error al escribir
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*$"))) {
                            if (newValue.length <= 2) {
                                chargeDay = newValue
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = chargeDayError != null,
                    errorMessage = chargeDayError
                )
            } else {
                if (chargeDay.isNotBlank()) {
                    chargeDay = ""
                }
            }




            DatePickerFieldToModal(
                label = "Inicio",
                modifier = Modifier,
                onSelectedDate = { d ->
                    if (d != null) {
                        selectedDate = d
                    }
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.padding(top = 32.dp))

            ButtonComponent(
                navController = {

                    if (isButtonEnabled) {
                        fixedVM.registerFixedExpense(
                            amount = expense,
                            description = description,
                            category = category,
                            type = type,
                            frequency = frequency,
                            chargeDay = chargeDay,
                            startDate = selectedDate!!
                        )
                        navController.popBackStack()
                    }
                },
                label = "Registrar Gasto",
                enable = isButtonEnabled


            )
            Spacer(modifier = Modifier.padding(bottom = 32.dp))

        }
    }

}



