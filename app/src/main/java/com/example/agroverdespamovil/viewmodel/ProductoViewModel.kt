package com.example.agroverdespamovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroverdespamovil.model.Producto
import com.example.agroverdespamovil.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository = ProductoRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> = _productoSeleccionado.asStateFlow()

    // Cargar todos los productos
    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.obtenerProductos()
                _productos.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar productos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cargar un producto específico
    fun cargarProductoPorId(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.obtenerProductoPorId(id)
                _productoSeleccionado.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar producto"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Buscar productos por categoría
    fun buscarPorCategoria(categoria: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.buscarPorCategoria(categoria)
                _productos.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al buscar productos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Filtrar solo productos orgánicos
    fun filtrarOrganicos() {
        val organicos = _productos.value.filter { it.certificacionOrganica }
        _productos.value = organicos
    }

    // Limpiar error
    fun clearError() {
        _error.value = null
    }
}