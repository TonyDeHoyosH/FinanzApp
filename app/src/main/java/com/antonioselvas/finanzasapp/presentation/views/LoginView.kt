package com.antonioselvas.finanzasapp.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.R
import com.antonioselvas.finanzasapp.components.ButtonComponent
import com.antonioselvas.finanzasapp.components.TextFieldComponent
import primaryColor
import primaryText
import secondaryText


const val LOGIN_ROUTE = "loginView"

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginView() {
    Scaffold(
        
    ) {
        LoginContent(it)
    }
}



@Composable
fun LoginContent(paddingValues: PaddingValues) {
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
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login Image",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.height(32.dp))
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
                value = "",
                onValue = {}
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
                value = "",
                onValue = {}
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ButtonComponent(
            navController = {},
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
                "¿Aun no tienes cuenta?",
                color = secondaryText
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.clickable(onClick = { /*TODO*/ }),
                text = "Registrate",
                color = primaryColor,
                textDecoration = TextDecoration.Underline,
            )

            }
        }
    }
}
