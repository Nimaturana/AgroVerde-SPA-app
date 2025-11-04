package com.example.agroverdespamovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroverdespamovil.model.Usuario
import com.example.agroverdespamovil.model.LoginRequest
import com.example.agroverdespamovil.model.SignupRequest
import com.example.agroverdespamovil.data.repository.UsuarioRepository
import com.example.agroverdespamovil.data.local.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val preferencesManager: PreferencesManager? = null
) : ViewModel() {

    // ✅ CAMBIO: Repository como companion object (compartido entre todas las instancias)
    companion object {
        private val repository = UsuarioRepository()
    }

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // ✅ LOGIN
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _isAuthenticated.value = false

            try {
                println("🔑 Intentando login con: $email")
                val request = LoginRequest(email, password)
                val response = repository.login(request)

                preferencesManager?.saveAuthToken(response.authToken)
                _usuario.value = response.user
                _isAuthenticated.value = true

                println("✅ Login exitoso para: ${response.user.email}")

            } catch (e: Exception) {
                println("❌ Error en login: ${e.message}")
                _error.value = e.message ?: "Error al iniciar sesión"
                _isAuthenticated.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ✅ REGISTRO
    fun signup(
        email: String,
        password: String,
        nombre: String,
        apellido: String? = null,
        telefono: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _isAuthenticated.value = false  // ← IMPORTANTE: false en registro

            try {
                println("📝 Registrando usuario: $email")
                val request = SignupRequest(
                    email = email,
                    password = password,
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono
                )
                val response = repository.signup(request)

                preferencesManager?.saveAuthToken(response.authToken)
                _usuario.value = response.user

                // ✅ NO ponemos isAuthenticated = true aquí
                // Para que vaya a login después del registro

                println("✅ Usuario registrado: ${response.user.email}")

            } catch (e: Exception) {
                println("❌ Error en registro: ${e.message}")
                _error.value = e.message ?: "Error al registrar usuario"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener perfil del usuario autenticado
    fun obtenerPerfil() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val user = repository.obtenerPerfil()
                _usuario.value = user
                _isAuthenticated.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al obtener perfil"
                _isAuthenticated.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            preferencesManager?.clearAuthToken()
            _usuario.value = null
            _isAuthenticated.value = false
        }
    }

    // Limpiar error
    fun clearError() {
        _error.value = null
    }

    // ✅ NUEVO: Resetear estado después del registro
    fun resetAuthState() {
        _isAuthenticated.value = false
        _error.value = null
    }
}