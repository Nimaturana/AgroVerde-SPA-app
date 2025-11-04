package com.example.agroverdespamovil.model


data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String? = null,


    val categoria: String,                    // "Verduras", "Frutas", etc.
    val precio: Double,
    val unidadMedida: String,                // "kg", "unidad", "litro"
    val stock: Int,

    val productorId: Int,
    val nombreProductor: String? = null,

    val certificacionOrganica: Boolean = false,  // ← CLAVE para AgroVerde
    val region: String? = null,                  // ← Origen del producto

    val imagenUrl: String? = null,
    val calificacion: Double? = null,

    val estado: String = "DISPONIBLE"  // "DISPONIBLE", "AGOTADO"
)