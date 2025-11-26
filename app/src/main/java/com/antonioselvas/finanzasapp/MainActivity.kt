package com.antonioselvas.finanzasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.antonioselvas.finanzasapp.presentation.navManager.NavManager
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import com.antonioselvas.finanzasapp.viewModels.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginVM: AuthViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme{
                NavManager(loginVM)
            }
        }
    }
}

