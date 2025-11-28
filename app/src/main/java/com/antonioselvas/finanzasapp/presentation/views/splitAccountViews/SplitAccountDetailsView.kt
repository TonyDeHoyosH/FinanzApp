package com.antonioselvas.finanzasapp.presentation.views.splitAccountViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.CardSplitAccount
import com.antonioselvas.finanzasapp.components.CardSplitAccountAddUser
import com.antonioselvas.finanzasapp.domain.models.SplitAccountUser
import gradientYellow
import primaryColor
import primaryText


const val SPLIT_ACCOUNT_DETAIL_ROUTE = "SplitAccountDetail"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitAccountDetailsView(navController: NavHostController) {
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
                        text = "Nombre de gasto",
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
            onShowAddFriend = { s -> showAddFriend = s},
        )
    }
}


@Composable
fun SplitAccountDetailsContent(paddingValues: PaddingValues, showAddFriend: Boolean, onShowAddFriend: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 60.dp)
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
                        text = "$50.00",
                        fontSize = 46.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText
                    )
                }
            }
        }
        val users = remember {
            mutableStateListOf(
                SplitAccountUser(
                    id = "1",
                    name = "Luis",
                    amount = 200f,
                    paidAmount = 0f,
                    paid = false,
                    deleted = false,
                ),
                SplitAccountUser(
                    id = "2",
                    name = "Andrea",
                    amount = 400f,
                    paidAmount = 0f,
                    paid = false,
                    deleted = false,
                )
            )
        }




        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = users,
                key = { it.id }
            ){ item ->
                CardSplitAccount(
                    user = item,
                    onEdit = {},
                    onComplete = { onComplete ->
                        users -= onComplete
                        item.paid = !onComplete.paid
                    },
                    onDelete = { onDelete ->
                        users -= onDelete
                        item.deleted = !onDelete.deleted
                    },
                    modifier = Modifier.animateItem()
                )

            }
        }
        if (showAddFriend){
            CardSplitAccountAddUser(
                onDismissRequest = { onShowAddFriend(false)},
                createdUser =  {user -> users.add(user) },
                users = users
            )
        }
    }
}