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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.CardSplitAccount
import com.antonioselvas.finanzasapp.components.CardSplitAccountAddUser
import com.antonioselvas.finanzasapp.components.DatePickerFieldToModal
import com.antonioselvas.finanzasapp.components.DropDownComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.domain.models.SplitAccountUser
import primaryColor
import primaryText
import secondaryText


const val NEW_SPLIT_ACCOUNT_ROUTE = "NewSplitAccount"

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NewSplitAccountView() {
    var expense by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var divisionForm by remember { mutableStateOf("") }
    val users: MutableList<SplitAccountUser> = remember {
        mutableStateListOf(
            SplitAccountUser(
                id = "1",
                name = "Emilia",
                amount = 300f,
                paidAmount = 0f,
                paid = false,
                deleted = false
            ),
            SplitAccountUser(
                id = "2",
                name = "Andrea",
                amount = 300f,
                paidAmount = 0f,
                paid = false,
                deleted = false
            )
        )
    }

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
                        onClick = { }
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
            expense = expense,
            onExpenseChange = { e -> expense = e },
            category = category,
            onCategory = { c -> category = c },
            type = type,
            onType = { t -> type = t },
            onSelectedDate = { d -> selectedDate = d },
            division = divisionForm,
            onDivisionForm = { v -> divisionForm = v },
            users = users,
            description = description,
            onDescription = { p -> description = p},
            addUser = { u -> users.add(u) }
        )
    }
}

@Composable
fun NewSplitAccountContent(
    paddingValues: PaddingValues,
    expense: String,
    onExpenseChange: (String) -> Unit,
    category: String,
    onCategory: (String) -> Unit,
    type: String,
    onType: (String) -> Unit,
    onSelectedDate: (Long) -> Unit,
    division: String,
    onDivisionForm: (String) -> Unit,
    users: MutableList<SplitAccountUser>,
    addUser: (SplitAccountUser) -> Unit,
    description: String,
    onDescription: (String) -> Unit,
) {

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

    val divisionForm: MutableList<String> = remember {
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
                        onExpenseChange(newValue)
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
                    onDescription(it)
                }
            )

            DropDownComponent(
                label = "Categoria",
                listOfCategories = categories,
                selectedText = category,
                onSelectedText = { c -> onCategory(c) }
            )

            DropDownComponent(
                label = "Forma División",
                listOfCategories = divisionForm,
                selectedText = division,
                onSelectedText = { v -> onDivisionForm(v) }
            )

            DropDownComponent(
                label = "Tipo",
                listOfCategories = types,
                selectedText = type,
                onSelectedText = { t -> onType(t) }
            )


            DatePickerFieldToModal(
                modifier = Modifier,
                onSelectedDate = { d ->
                    if (d != null) {
                        onSelectedDate(d)
                    }
                }
            )
        }




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
                            showAddFriend = true
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

            if (showAddFriend) {
                CardSplitAccountAddUser(
                    createdUser = { user -> addUser(user) },
                    onDismissRequest = { showAddFriend = false },
                    users = users
                )
            }

            LazyColumn(
                modifier = Modifier.height(160.dp)
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





            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ButtonComponent(
                    navController = {

                    },
                    label = "Registrar Gasto",
                    enable = true


                )
                Spacer(modifier = Modifier.padding(bottom = 32.dp))

            }
        }

    }

}


