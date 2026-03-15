package com.example.hospital.data.repository

import com.example.hospital.core.network.RetrofitClient
import com.example.hospital.data.model.Personal

object PersonalRepository {
    // Ahora devuelve la lista completa de personal que viene en "data"
    suspend fun obtenerPersonal(): List<Personal> {
        val response = RetrofitClient.api.obtenerPersonal()
        
        if (response.status == "success") {
            return response.data
        } else {
            throw Exception("Error al obtener personal: ${response.status}")
        }
    }
}

