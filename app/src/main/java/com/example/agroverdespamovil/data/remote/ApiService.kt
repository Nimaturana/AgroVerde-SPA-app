package com.example.agroverdespamovil.data.remote

import com.example.agroverdespamovil.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/"
    }

    // ========== AUTENTICACIÓN ==========

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>

    @GET("auth/me")
    suspend fun obtenerPerfil(): Response<Usuario>

    // ========== PRODUCTOS ==========

    @GET("productos")
    suspend fun getProductos(): Response<List<Producto>>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): Response<Producto>

    // Agrega más endpoints según necesites
}