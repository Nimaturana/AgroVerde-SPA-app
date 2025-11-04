package com.example.agroverdespamovil.data.remote

import com.example.agroverdespamovil.data.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val preferencesManager: PreferencesManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Obtener token de forma síncrona
        val token = preferencesManager.getAuthTokenSync()

        // Si hay token, agregarlo al header
        val newRequest = if (token.isNotEmpty()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}