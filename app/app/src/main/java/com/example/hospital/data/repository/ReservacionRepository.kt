package com.example.hospital.data.repository

import com.example.hospital.core.network.RetrofitClient
import com.example.hospital.data.model.ReservacionRequest
import com.example.hospital.data.model.SimpleStatusResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

object ReservacionRepository {
    private val gson = Gson()
    
    suspend fun crearReservacion(request: ReservacionRequest) {
        try {
            val response = RetrofitClient.api.crearReservacion(request)
            if (response.status != "success") {
                // El backend respondió con status != "success", mostrar su mensaje
                throw Exception(response.message ?: "Error al crear reservación")
            }
        } catch (e: HttpException) {
            // Error HTTP (400, 401, 500, etc.)
            // Intentar parsear el cuerpo de error si está disponible
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (errorBody != null) {
                try {
                    // Intentar parsear como SimpleStatusResponse para obtener el mensaje
                    // Si el backend devuelve {"status":"error","message":"DNI incorrecto"}
                    val errorResponse = gson.fromJson(errorBody, SimpleStatusResponse::class.java)
                    errorResponse.message ?: "Error del servidor: ${e.code()}"
                } catch (ex: JsonSyntaxException) {
                    // Si no se puede parsear, intentar buscar "message" en el texto
                    if (errorBody.contains("\"message\"", ignoreCase = true)) {
                        try {
                            val messageStart = errorBody.indexOf("\"message\"") + 10
                            val messageEnd = errorBody.indexOf("\"", messageStart + 1)
                            if (messageEnd > messageStart) {
                                errorBody.substring(messageStart, messageEnd)
                            } else {
                                "Error del servidor: ${e.code()}"
                            }
                        } catch (ex2: Exception) {
                            "Error del servidor: ${e.code()}"
                        }
                    } else {
                        "Error del servidor: ${e.code()}"
                    }
                } catch (ex: Exception) {
                    "Error del servidor: ${e.code()}"
                }
            } else {
                "Error del servidor: ${e.code()} - ${e.message()}"
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            // Error de red/conexión
            throw Exception("Error de conexión. Verifica tu internet e intenta nuevamente.")
        } catch (e: Exception) {
            // Cualquier otro error (incluyendo los que lanzamos nosotros)
            throw e
        }
    }
}


