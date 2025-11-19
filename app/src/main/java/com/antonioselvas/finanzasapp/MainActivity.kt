package com.antonioselvas.finanzasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.antonioselvas.finanzasapp.presentation.navManager.NavManager
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme{
                NavManager()
            }
        }
    }
}

