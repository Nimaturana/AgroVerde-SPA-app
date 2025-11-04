package com.example.agroverdespamovil.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String? = null,

    @SerializedName("telefono")
    val telefono: String? = null,

    @SerializedName("tipo_usuario")
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("direccion")
    val direccion: String? = null,

    @SerializedName("ubicacion")
    val ubicacion: Ubicacion? = null,

    @SerializedName("fecha_registro")
    val fechaRegistro: String? = null,

    @SerializedName("activo")
    val activo: Boolean = true
)

enum class TipoUsuario {
    CLIENTE,
    PRODUCTOR,
    TRANSPORTISTA,
    ADMIN
}

// ========== AUTENTICACIÓN ==========

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

data class SignupRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String? = null,

    @SerializedName("telefono")
    val telefono: String? = null
    // ← QUITÉ tipoUsuario de aquí

)

// Respuestas de la API
data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String,

    @SerializedName("user")
    val user: Usuario
)

// Alias para mantener compatibilidad
typealias SignupResponse = LoginResponse
typealias AuthResponse = LoginResponse