package com.example.agroverdespamovil.data.repository

import com.example.agroverdespamovil.model.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class UsuarioRepository {

    // ========== BASE DE DATOS SIMULADA ==========
    private val usuariosRegistrados = mutableListOf(
        Usuario(
            id = 1,
            email = "demo@agroverde.cl",
            nombre = "Usuario Demo",
            apellido = "Test",
            telefono = "+56912345678",
            tipoUsuario = TipoUsuario.CLIENTE,
            avatarUrl = null,
            direccion = "Santiago, Chile",
            ubicacion = null,
            fechaRegistro = "2024-01-01",
            activo = true
        )
    )

    private val credenciales = mutableMapOf(
        "demo@agroverde.cl" to "123456"
    )

    private var nextUserId = 2

    // ✅ NUEVO: Trackear usuario autenticado actual
    private var usuarioActualId: Int? = null

    // ========== LOGIN ==========
    suspend fun login(request: LoginRequest): AuthResponse {
        delay(1500)

        val passwordGuardada = credenciales[request.email.lowercase()]

        if (passwordGuardada == null) {
            throw Exception("El correo no está registrado")
        }

        if (passwordGuardada != request.password) {
            throw Exception("Contraseña incorrecta")
        }

        val usuario = usuariosRegistrados.first {
            it.email.lowercase() == request.email.lowercase()
        }

        // ✅ Guardar ID del usuario autenticado
        usuarioActualId = usuario.id

        return AuthResponse(
            authToken = "token_${usuario.id}_${System.currentTimeMillis()}",
            user = usuario
        )
    }

    // ========== REGISTRO ==========
    suspend fun signup(request: SignupRequest): AuthResponse {
        delay(1500)

        if (credenciales.containsKey(request.email.lowercase())) {
            throw Exception("Este correo ya está registrado")
        }

        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date())

        val nuevoUsuario = Usuario(
            id = nextUserId++,
            email = request.email.lowercase(),
            nombre = request.nombre,
            apellido = request.apellido,
            telefono = request.telefono,
            tipoUsuario = TipoUsuario.CLIENTE,
            avatarUrl = null,
            direccion = null,
            ubicacion = null,
            fechaRegistro = fechaActual,
            activo = true
        )

        usuariosRegistrados.add(nuevoUsuario)
        credenciales[request.email.lowercase()] = request.password

        // ✅ NO guardar usuarioActualId aquí (el usuario va a login después)

        return AuthResponse(
            authToken = "token_${nuevoUsuario.id}_${System.currentTimeMillis()}",
            user = nuevoUsuario
        )
    }

    // ========== OBTENER PERFIL ==========
    suspend fun obtenerPerfil(): Usuario {
        delay(800)

        // ✅ CAMBIO: Devolver usuario autenticado actual, no el primero
        val id = usuarioActualId ?: throw Exception("No hay usuario autenticado")

        return usuariosRegistrados.firstOrNull { it.id == id }
            ?: throw Exception("Usuario no encontrado")
    }

    // ========== ACTUALIZAR PERFIL ==========
    suspend fun actualizarPerfil(usuario: Usuario): Usuario {
        delay(1000)

        val index = usuariosRegistrados.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            usuariosRegistrados[index] = usuario
            return usuario
        }

        throw Exception("Usuario no encontrado")
    }

    // ✅ NUEVO: Método para logout
    fun logout() {
        usuarioActualId = null
    }
}