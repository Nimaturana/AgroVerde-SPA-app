package com.example.agroverdespamovil.data.repository

import com.example.agroverdespamovil.model.*
import java.util.Date

class PedidoRepository {

    // TODO: Implementar con ApiService cuando esté listo
    suspend fun obtenerPedidos(): List<Pedido> {
        // Datos de prueba temporales
        return listOf(
            Pedido(
                id = 1,
                clienteId = 1,
                productos = listOf(
                    ProductoPedido(
                        productoId = 1,
                        nombreProducto = "Tomates Orgánicos",
                        cantidad = 2,
                        precioUnitario = 2500.0,
                        subtotal = 5000.0
                    )
                ),
                total = 5000.0,
                estado = EstadoPedido.EN_PROCESO,
                fechaCreacion = Date(),
                fechaEntrega = null,
                direccionEntrega = "Santiago Centro",
                rutaAsignada = null,
                observaciones = "Entregar en la mañana"
            )
        )
    }

    suspend fun obtenerPedidoPorId(id: Int): Pedido {
        val pedidos = obtenerPedidos()
        return pedidos.first { it.id == id }
    }

    suspend fun crearPedido(pedido: Pedido): Pedido {
        // Simulación temporal
        return pedido.copy(id = 999)
    }

    suspend fun cancelarPedido(id: Int) {
        // Simulación temporal
    }
}