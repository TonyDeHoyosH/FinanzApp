package com.antonioselvas.finanzasapp.presentation.views.splitAccountViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateListOf
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
import com.antonioselvas.finanzasapp.components.CardSplitAccount
import com.antonioselvas.finanzasapp.components.CardSplitAccountAddUser
import com.antonioselvas.finanzasapp.components.DatePickerFieldToModal
import com.antonioselvas.finanzasapp.components.DropDownComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitAccountViewModel
import primaryColor
import primaryText
import secondaryText


const val NEW_SPLIT_ACCOUNT_ROUTE = "NewSplitAccount"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSplitAccountView(navController: NavController, splitVM: SplitAccountViewModel) {

    val users: MutableList<SplitAccount> = remember { mutableStateListOf() }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Nuevo Gasto Compartido",
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
        NewSplitAccountContent(
            it,
            users = users,
            addUser = { u -> users.add(u) },
            navController,
            splitVM
        )
    }
}

@Composable
fun NewSplitAccountContent(
    paddingValues: PaddingValues,
    users: MutableList<SplitAccount>,
    addUser: (SplitAccount) -> Unit,
    navController: NavController,
    splitVM: SplitAccountViewModel,
) {

    var expense by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var divisionForm by remember { mutableStateOf("") }
    var subTotal: Double = 0.0

    var showAddFriend by remember { mutableStateOf(false) }

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

    val divisionForms: MutableList<String> = remember {
        mutableListOf(
            "Equitativo",
            "Personalizado"
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
                label = "Forma División",
                listOfCategories = divisionForms,
                selectedText = divisionForm,
                onSelectedText = { divisionForm = it }
            )

            DropDownComponent(
                label = "Tipo",
                listOfCategories = types,
                selectedText = type,
                onSelectedText = { type = it }
            )


            DatePickerFieldToModal(
                modifier = Modifier,
                onSelectedDate = { d ->
                    if (d != null) {
                        selectedDate = d
                    }
                }
            )
        }

        users.forEach { user ->
            subTotal = subTotal + user.amount
        }
        val isEquitable = divisionForm == "Equitativo"

        val totalExpense = expense.toDoubleOrNull() ?: 0.0



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clickable(
                        onClick = {
                            if (totalExpense > 0.0) {
                                showAddFriend = true
                            }
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Agregar amigo")
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddUser"
                )

            }


            if (isEquitable && totalExpense > 0.0 && users.isNotEmpty()) {
                val individualAmount = totalExpense / (users.size + 1)
                users.forEachIndexed { index, user ->
                    users[index] = user.copy(amount = individualAmount)
                }
            }


            if (showAddFriend && totalExpense > 0.0) {
                CardSplitAccountAddUser(
                    createdUser = { user -> addUser(user) },
                    onDismissRequest = { showAddFriend = false },
                    users = users,
                    isEquitable = isEquitable,
                    subTotal = subTotal,
                    total = totalExpense,
                )
            }


            Text("SubTotal: $${String.format("%.2f", subTotal)}")
            Text("Monto restante: $${String.format("%.2f", totalExpense - subTotal)}")
            LazyColumn(
                modifier = Modifier
                    .height(160.dp)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                items(
                    users,
                    key = { it.id }) { user ->
                    CardSplitAccount(
                        user = user,
                        onEdit = {},
                        onComplete = { onComplete ->
                            users -= onComplete
                            user.paid = !onComplete.paid
                        },
                        onDelete = { onDelete ->
                            users -= onDelete
                            user.deleted = !onDelete.deleted
                        },
                        modifier = Modifier.animateItem()
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }


            val isFormValid =
                expense.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty() && type.isNotEmpty() && selectedDate.toString()
                    .isNotEmpty() && divisionForm.isNotEmpty()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ButtonComponent(
                    navController = {
                        splitVM.addSplitAccount(
                            amount = expense.toDouble(),
                            description = description,
                            category = category,
                            type = type,
                            date = selectedDate!!,
                            divisionForm = divisionForm,
                            users = users,
                            typeTransaction = "expense"
                        )
                        navController.navigate(SPLIT_ACCOUNT_ROUTE)
                    },
                    label = "Registrar Gasto",
                    enable = isFormValid


                )
                Spacer(modifier = Modifier.padding(bottom = 32.dp))

            }
        }

    }

}


