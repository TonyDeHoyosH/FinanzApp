package com.antonioselvas.finanzasapp.presentation.views.homeViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Output
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.ProfilePictureComponent
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.WELCOME_ROUTE
import com.antonioselvas.finanzasapp.presentation.viewModels.AuthViewModel
import com.antonioselvas.finanzasapp.presentation.viewModels.HomeViewModel
import primaryColor
import red
import androidx.compose.runtime.collectAsState

const val USER_ACCOUNT_ROUTE = "user_account"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAccountView(
    rootNavController: NavHostController,
    loginVM: AuthViewModel,
    navController: NavHostController,
    homeVM: HomeViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text("")
                },
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        UserAccountContent(rootNavController, loginVM, homeVM)
    }
}


@Composable
fun UserAccountContent(
    rootNavController: NavHostController,
    loginVM: AuthViewModel,
    homeVM: HomeViewModel
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.White)
        ) {
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().
                    fillMaxSize()
                .padding(top = 16.dp)
                .background(color = primaryColor)
        ) {
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            Box(
                modifier = Modifier
            ){
                Text(
                    text = homeVM.userInfo.collectAsState().value.name,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }



            Button(
                onClick = { /* TODO: Handle edit profile */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5865F2)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                Text(
                    "Editar perfil",
                    color = Color.White

                )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Button(
                onClick = {
                    loginVM.auth.signOut()

                    rootNavController.navigate(WELCOME_ROUTE) {
                        popUpTo("splash_graph") { inclusive = true }
                        launchSingleTop = true
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Output,
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                    Text(
                        "Cerrar sesion",
                        color = Color.White

                    )
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .offset(x = 16.dp, y = 80.dp)
    ) {
        ProfilePictureComponent()
    }
}