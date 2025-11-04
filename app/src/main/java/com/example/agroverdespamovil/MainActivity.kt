package com.example.agroverdespamovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.agroverdespamovil.navigation.NavGraph
import com.example.agroverdespamovil.ui.theme.AgroVerdeSPAMovilTheme
import com.example.agroverdespamovil.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AgroVerdeSPAMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Crea ViewModel
                    val sharedAuthViewModel: AuthViewModel = viewModel()

                    NavGraph(
                        navController = navController,
                        authViewModel = sharedAuthViewModel,  // ← Pasar como parámetro
                        startDestination = "login"
                    )
                }
            }
        }
    }
}