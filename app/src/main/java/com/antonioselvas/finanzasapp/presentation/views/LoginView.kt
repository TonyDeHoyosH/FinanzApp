package com.antonioselvas.finanzasapp.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.components.Alert
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.PasswordTextFieldComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import com.antonioselvas.finanzasapp.presentation.viewModels.AuthViewModel
import primaryColor
import primaryText
import secondaryText


const val LOGIN_ROUTE = "login"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavHostController, loginVM: AuthViewModel) {
    Scaffold(
        containerColor = Color.White
    ) {
        LoginContent(it, navController, loginVM)
    }
}



@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    loginVM: AuthViewModel
) {
    var isLoading by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 18.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login Image",
            modifier = Modifier.size(260.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text ="Login",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = primaryText
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Empieza ya a cuidar tu dinero con\n" +
                    "FinanzasApp",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = secondaryText,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(12.dp))
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
                label = "Correo Electronico",
                placeHolder = "Ingrese su correo electronico",
                value = email,
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
            PasswordTextFieldComponent(
                label = "Contraseña",
                placeHolder = "Ingrese su contraseña",
                value = password,
                onValue = { password = it }
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        ButtonComponent(
            navController = {
                if (!isLoading) {
                    isLoading = true
                    loginVM.login(email, password) {
                        isLoading = false
                        navController.navigate("main_graph") {
                            popUpTo(LOGIN_ROUTE) { inclusive = true }
                        }
                    }
                }
            },
            label = if (isLoading) "Cargando..." else "Continuar",
            enable = email.isNotBlank() && password.isNotBlank() && !isLoading
        )


            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
            Text(
                "¿Aun no tienes cuenta?",
                color = secondaryText
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.clickable(onClick = { navController.navigate(REGISTER_ROUTE) }),
                text = "Registrate",
                color = primaryColor,
                textDecoration = TextDecoration.Underline,
            )

            }
        if (loginVM.showAlert) {
            Alert(
                title = "Alerta",
                message = "Usuario y/o contraseña incorrectos",
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
