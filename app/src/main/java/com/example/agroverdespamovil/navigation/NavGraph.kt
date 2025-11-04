package com.example.agroverdespamovil.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agroverdespamovil.ui.theme.screen.*
import com.example.agroverdespamovil.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    startDestination: String = "login"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(route = "login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Registro
        composable(route = "registro") {
            RegistroScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Home
        composable(route = "home") {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Productos (si existe esta ruta)
        composable(route = "productos") {
            ProductosScreen(navController = navController)
        }

        // Detalle de producto
        composable(
            route = "detalle_producto/{productoId}",
            arguments = listOf(
                navArgument("productoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            DetalleProductoScreen(
                navController = navController,
                productoId = productoId
            )
        }

        // Pedidos
        composable(route = "pedidos") {
            PedidoScreen(navController = navController)
        }

        // Perfil - ✅ AQUÍ está el cambio importante
        composable(route = "perfil") {
            PerfilScreen(
                navController = navController,
                authViewModel = authViewModel  //  Pasar AuthViewModel
            )
        }
    }
}