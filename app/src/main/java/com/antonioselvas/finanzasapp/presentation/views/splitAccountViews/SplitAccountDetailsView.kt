package com.antonioselvas.finanzasapp.presentation.views.splitAccountViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.CardSplitAccount
import com.antonioselvas.finanzasapp.components.CardSplitAccountAddUser
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitAccountViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.SplitUiStateDetails
import gradientYellow
import primaryColor
import primaryText


const val SPLIT_ACCOUNT_DETAIL_ROUTE = "SplitAccountDetail"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitAccountDetailsView(
    navController: NavHostController,
    splitVM: SplitAccountViewModel,
    id: String?
) {

    val uiStateDetails by splitVM.uiStateDetails.collectAsState()

    id?.let {
        LaunchedEffect(key1 = it) {
            splitVM.getSplitAccountDetails(it)
        }
    }
    var showAddFriend by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(gradientYellow, Color.White),
                    startY = 0.0f,
                    endY = 1500f
                )
            ),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = uiStateDetails.splitAccount?.description ?: "",
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
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = {
                    showAddFriend = true
                },
                containerColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddBalance",
                    tint = primaryColor
                )
            }
        }
    ) {
        SplitAccountDetailsContent(
            it, showAddFriend,
            onShowAddFriend = { s -> showAddFriend = s },
            splitVM,
            uiStateDetails
        )
    }
}


@Composable
fun SplitAccountDetailsContent(
    paddingValues: PaddingValues,
    showAddFriend: Boolean,
    onShowAddFriend: (Boolean) -> Unit,
    splitVM: SplitAccountViewModel,
    uiStateDetails: SplitUiStateDetails
) {
    if (uiStateDetails.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (uiStateDetails.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${uiStateDetails.error}")
        }
        return
    }

    val transaction = uiStateDetails.splitAccount

    if (transaction == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error")
        }
        return
    }


    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .width(234.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gasto Total:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = primaryText
                    )
                    Text(
                        text = "\$${String.format("%.2f", transaction.amount)}",
                        fontSize = 46.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                }
            }
        }
        val totalPaid = transaction.users.sumOf { it.paidAmount }
        val totalDue = transaction.users.sumOf { it.amount }
        val globalRemainingDebt = totalDue - totalPaid

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Deuda total:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = primaryText
            )
            Text(
                text = "\$${String.format("%.2f", globalRemainingDebt)}",
                fontSize = 46.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryText
            )
        }

        Spacer(Modifier.padding(vertical = 16.dp))
        var users = transaction.users
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = users,
                key = { it.id }
            ) { item ->
                CardSplitAccount(
                    user = item,
                    onEdit = {},
                    onComplete = { userToComplete ->
                        splitVM.markUserAsPaid(
                            transactionId = transaction.id,
                            debtorUserId = item.id
                        )
                    },
                    onDelete = { userToDelete ->
                        if (userToDelete.paidAmount == 0.0) {
                            splitVM.removeUserFromSplitAccount(
                                transactionId = transaction.id,
                                debtorUserId = item.id
                            )
                        } else {

                        }
                    },
                    showEditButton = transaction.divisionForm == "Personalizado",
                    modifier = Modifier.animateItem()
                )

            }
        }

        val isEquitableDivision = transaction.divisionForm == "Equitativo"
        if (showAddFriend) {
            CardSplitAccountAddUser(
                onDismissRequest = { onShowAddFriend(false) },
                createdUser = { newUser ->
                    splitVM.addUserToSplitAccount(
                        transactionId = transaction.id,
                        newUser = newUser
                    )
                },
                users = transaction.users,
                isEquitable = isEquitableDivision,
                total = transaction.amount,
                subTotal = transaction.users.sumOf { it.amount }
            )
        }
    }
}