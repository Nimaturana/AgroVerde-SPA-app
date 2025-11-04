package com.example.agroverdespamovil.data.repository

import com.example.agroverdespamovil.model.Producto

class ProductoRepository {

    // TODO: Implementar con ApiService cuando esté listo
    suspend fun obtenerProductos(): List<Producto> {
        // Datos de prueba temporales
        return listOf(
            Producto(
                id = 1,
                nombre = "Tomates Orgánicos",
                descripcion = "Tomates frescos cultivados sin pesticidas",
                categoria = "Verduras",
                precio = 2500.0,
                unidadMedida = "kg",
                stock = 50,
                productorId = 1,
                nombreProductor = "Juan Pérez",
                certificacionOrganica = true,
                region = "Región Metropolitana",
                imagenUrl = null,
                calificacion = 4.5,
                estado = "DISPONIBLE"
            ),
            Producto(
                id = 2,
                nombre = "Lechugas Hidropónicas",
                descripcion = "Lechugas frescas cultivadas en invernadero",
                categoria = "Verduras",
                precio = 1500.0,
                unidadMedida = "unidad",
                stock = 30,
                productorId = 2,
                nombreProductor = "María González",
                certificacionOrganica = true,
                region = "Valparaíso",
                imagenUrl = null,
                calificacion = 4.8,
                estado = "DISPONIBLE"
            )
        )
    }

    suspend fun obtenerProductoPorId(id: Int): Producto {
        val productos = obtenerProductos()
        return productos.first { it.id == id }
    }

    suspend fun buscarPorCategoria(categoria: String): List<Producto> {
        val productos = obtenerProductos()
        return productos.filter { it.categoria == categoria }
    }
}