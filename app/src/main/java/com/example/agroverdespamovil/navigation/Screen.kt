package com.example.agroverdespamovil.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Productos : Screen("productos")
    object DetalleProducto : Screen("detalle_producto/{productoId}") {
        fun createRoute(productoId: Int) = "detalle_producto/$productoId"
    }
    object Pedidos : Screen("pedidos")
    object Perfil : Screen("perfil")
}