package com.antonioselvas.finanzasapp.presentation.views.homeViews

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
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.DatePickerFieldToModal
import com.antonioselvas.finanzasapp.components.DropDownComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.presentation.viewModels.HomeViewModel
import primaryColor
import primaryText
import secondaryText


const val NEW_EXPENSE_ROUTE = "NewExpense"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExpenseView(navController: NavHostController, homeViewModel: HomeViewModel) {


    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Nuevo Gasto",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
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
        NewExpenseContent(
            it,
            navController,
            homeViewModel
        )
    }
}


@Composable
fun NewExpenseContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    homeViewModel: HomeViewModel,

    ) {
    var expense by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var categories: MutableList<String> = remember {
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
            modifier = Modifier
                .height(360.dp)
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            TextFieldComponent(
                label = "Descripcion",
                placeHolder = "Ej: Café con amigos",
                value = description,
                onValue = { p ->
                    description = p
                }
            )
            DropDownComponent(
                label = "Categoria",
                listOfCategories = categories,
                selectedText = category,
                onSelectedText = { c -> category = c }
            )
            DropDownComponent(
                label = "Tipo",
                listOfCategories = types,
                selectedText = type,
                onSelectedText = { t -> type = t }
            )
            DatePickerFieldToModal(
                modifier = Modifier,
                onSelectedDate = { d -> selectedDate = d }
            )


        }
        val isFormValid = expense.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            ButtonComponent(
                navController = {
                    homeViewModel.addExpense(
                        amount = expense.toDouble(),
                        description = description,
                        category = category,
                        type = type,
                        date = selectedDate,
                        isShared = false
                    )
                    navController.navigate(HOME_ROUTE)
                },
                label = "Registrar Gasto",
                enable = isFormValid


            )
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }


    }
}


