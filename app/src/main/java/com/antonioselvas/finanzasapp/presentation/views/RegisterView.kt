package com.antonioselvas.finanzasapp.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.dialog.Alert
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.components.Alert
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.presentation.views.onboardingViews.GOAL_ROUTE
import com.antonioselvas.finanzasapp.viewModels.AuthViewModel
import primaryColor
import primaryText
import secondaryText


const val REGISTER_ROUTE = "register"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(navController: NavHostController, loginVM: AuthViewModel) {
    Scaffold(
    ) {
        RegisterContent(it, navController, loginVM)
    }
}


@Composable
fun RegisterContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    loginVM: AuthViewModel
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.register),
            contentDescription = "Login Image",
            modifier = Modifier.size(245.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text ="Registrate",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = primaryText
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(44.dp)
                    .height(38.dp),
                imageVector = Icons.Outlined.Person,
                contentDescription = "Email",
                tint = secondaryText
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextFieldComponent(
                label = "Username",
                placeHolder = "Ingresa tu nombre",
                value = name,
                onValue = { name = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(44.dp)
                    .height(38.dp),
                imageVector = Icons.Outlined.Mail,
                contentDescription = "Email",
                tint = secondaryText
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextFieldComponent(
                label = "Correo",
                placeHolder = "Ingrese su correo electronico",
                value = email ,
                onValue = {email = it}
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(44.dp)
                    .height(38.dp),
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Email",
                tint = secondaryText
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextFieldComponent(
                label = "Contraseña",
                placeHolder = "Ingrese su contraseña",
                value = password,
                onValue = { password = it }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ButtonComponent(
            navController = {
                loginVM.register(
                    email,
                    password,
                    name
                ) { navController.navigate(GOAL_ROUTE) }
            },
            label = "Continuar",
            enable = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight(),

            ) {
            Row {
                Text(
                    "¿Ya estas registrado?",
                    color = secondaryText
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(LOGIN_ROUTE)
                    }),
                    text = "login",
                    color = primaryColor,
                    textDecoration = TextDecoration.Underline,
                )
                if (loginVM.showAlert){
                    Alert(
                        title = "Alerta",
                        message = "Usuario no creado",
                        confirmText = "Aceptar",
                        onConfirmClick = {
                            loginVM.closeAlert()
                            email = ""
                            password = ""
                        }
                    ) {
                        email = ""
                        password = ""
                    }

            }
        }
    }
}
}