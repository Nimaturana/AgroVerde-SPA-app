package com.example.agroverdespamovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroverdespamovil.model.Pedido
import com.example.agroverdespamovil.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidoViewModel(
    private val repository: PedidoRepository = PedidoRepository()
) : ViewModel() {

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _pedidoSeleccionado = MutableStateFlow<Pedido?>(null)
    val pedidoSeleccionado: StateFlow<Pedido?> = _pedidoSeleccionado.asStateFlow()

    // Cargar pedidos del usuario
    fun cargarPedidos() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.obtenerPedidos()
                _pedidos.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar pedidos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Crear nuevo pedido
    fun crearPedido(pedido: Pedido) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val nuevoPedido = repository.crearPedido(pedido)
                // Recargar lista de pedidos
                cargarPedidos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al crear pedido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener detalle de pedido
    fun cargarPedidoPorId(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val pedido = repository.obtenerPedidoPorId(id)
                _pedidoSeleccionado.value = pedido
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar pedido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cancelar pedido
    fun cancelarPedido(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.cancelarPedido(id)
                // Recargar lista de pedidos
                cargarPedidos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cancelar pedido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Limpiar error
    fun clearError() {
        _error.value = null
    }
}