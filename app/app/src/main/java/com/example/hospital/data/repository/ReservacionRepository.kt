package com.example.hospital.data.repository

import com.example.hospital.core.network.RetrofitClient
import com.example.hospital.data.model.ReservacionRequest

object ReservacionRepository {
    suspend fun crearReservacion(request: ReservacionRequest) {
        val response = RetrofitClient.api.crearReservacion(request)
        if (response.status != "success") {
            throw Exception(response.message ?: "Error al crear reservación")
        }
    }
}


