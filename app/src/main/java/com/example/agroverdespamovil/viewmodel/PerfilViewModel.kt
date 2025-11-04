package com.example.agroverdespamovil.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroverdespamovil.model.Usuario
import com.example.agroverdespamovil.data.repository.UsuarioRepository
import com.example.agroverdespamovil.data.local.LocalImageStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    context: Context  // recibe Contexto
) : ViewModel() {

    private val repository = UsuarioRepository()
    private val imageStorage = LocalImageStorage(context)  // inicializa

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _avatarUri = MutableStateFlow<Uri?>(null)
    val avatarUri: StateFlow<Uri?> = _avatarUri.asStateFlow()

    init {
        // Cargar avatar guardado al iniciar
        cargarAvatarGuardado()
    }

    private fun cargarAvatarGuardado() {
        viewModelScope.launch {
            try {
                val savedUri = imageStorage.getProfileImage()
                _avatarUri.value = savedUri
                println("✅ Avatar cargado: $savedUri")
            } catch (e: Exception) {
                println("⚠️ No hay avatar guardado")
            }
        }
    }

    // Cargar perfil del usuario
    fun cargarPerfil() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val user = repository.obtenerPerfil()
                _usuario.value = user
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar perfil"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Actualizar foto de perfil
    fun actualizarAvatar(imageUri: Uri) {
        viewModelScope.launch {
            try {
                // Guardar imagen localmente con persistencia
                val savedUri = imageStorage.saveProfileImage(imageUri)
                _avatarUri.value = savedUri
                println("✅ Avatar guardado: $savedUri")
            } catch (e: Exception) {
                _error.value = "Error al guardar imagen: ${e.message}"
                println("❌ Error: ${e.message}")
            }
        }
    }

    // Actualizar perfil
    fun actualizarPerfil(usuario: Usuario) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val usuarioActualizado = repository.actualizarPerfil(usuario)
                _usuario.value = usuarioActualizado
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al actualizar perfil"
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