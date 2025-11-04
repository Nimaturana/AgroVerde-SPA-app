package com.example.agroverdespamovil.model

import java.util.Date

data class Pedido(
    val id: Int,
    val clienteId: Int,
    val productos: List<ProductoPedido>,
    val total: Double,
    val estado: EstadoPedido,
    val fechaCreacion: Date,
    val fechaEntrega: Date? = null,
    val direccionEntrega: String,
    val rutaAsignada: Int? = null,
    val observaciones: String? = null
)

data class ProductoPedido(
    val productoId: Int,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)

enum class EstadoPedido {
    PENDIENTE,
    EN_PROCESO,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}