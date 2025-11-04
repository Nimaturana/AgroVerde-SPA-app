package com.example.agroverdespamovil.model

import java.util.Date

data class RutaEntrega(
    val id: Int,
    val transportistaId: Int,
    val pedidos: List<Int>, // IDs de pedidos en la ruta
    val estado: EstadoRuta,
    val fechaInicio: Date,
    val fechaFin: Date? = null,
    val puntos: List<PuntoRuta>,
    val distanciaTotal: Double? = null,
    val tiempoEstimado: Int? = null // en minutos
)

data class PuntoRuta(
    val orden: Int,
    val pedidoId: Int,
    val ubicacion: Ubicacion,
    val horaLlegadaEstimada: Date? = null,
    val horaLlegadaReal: Date? = null,
    val completado: Boolean = false
)

enum class EstadoRuta {
    PLANIFICADA,
    EN_CURSO,
    COMPLETADA,
    CANCELADA
}