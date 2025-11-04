package com.example.agroverdespamovil.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ClimaUtils {

    // API Key pública de prueba
    private const val API_KEY = "47282e88662875a6c7fa5c336ceae06c"

    suspend fun obtenerClima(ciudad: String = "Santiago,CL"): String {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                val urlString = "https://api.openweathermap.org/data/2.5/weather?q=$ciudad&appid=$API_KEY&units=metric&lang=es"
                val url = URL(urlString)

                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.setRequestProperty("Accept", "application/json")

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

                    val json = JSONObject(response)

                    val nombre = json.getString("name")
                    val temp = json.getJSONObject("main").getDouble("temp").toInt()
                    val sensacion = json.getJSONObject("main").getDouble("feels_like").toInt()
                    val humedad = json.getJSONObject("main").getInt("humidity")
                    val descripcion = json.getJSONArray("weather").getJSONObject(0).getString("description")
                    val viento = json.getJSONObject("wind").getDouble("speed")
                    val presion = json.getJSONObject("main").getInt("pressure")

                    """
                    🌤️ Clima en $nombre
                    
                    🌡️ Temperatura: $temp°C
                    💭 Sensación térmica: $sensacion°C
                    ☁️ Condición: ${descripcion.replaceFirstChar { it.uppercase() }}
                    💧 Humedad: $humedad%
                    🌬️ Viento: $viento km/h
                    🔽 Presión: $presion hPa
                    """.trimIndent()
                } else {
                    "❌ Error del servidor: código $responseCode"
                }

            } catch (e: Exception) {
                "❌ Error al obtener clima:\n${e.message ?: "Error desconocido"}"
            } finally {
                connection?.disconnect()
            }
        }
    }
}