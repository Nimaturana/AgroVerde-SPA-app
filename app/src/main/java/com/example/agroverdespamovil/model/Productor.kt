package com.example.agroverdespamovil.model

data class Productor(
    val id: Int,
    val nombre: String,
    val apellido: String? = null,
    val email: String,
    val telefono: String? = null,
    val ubicacion: Ubicacion? = null,
    val certificacionOrganica: Boolean = false,
    val descripcion: String? = null,
    val fotoPerfilUrl: String? = null,
    val productosOfrecidos: List<Int> = emptyList(), // IDs de productos
    val calificacion: Double? = null,
    val fechaRegistro: String? = null
)

data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val direccion: String? = null,
    val region: String? = null,
    val comuna: String? = null
)