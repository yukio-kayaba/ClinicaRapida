package com.example.hospital.data.repository

import com.example.hospital.core.network.RetrofitClient
import com.example.hospital.data.model.Personal

object PersonalRepository {
    suspend fun obtenerPersonal(): Personal {
        val response = RetrofitClient.api.obtenerPersonal()
        
        if (response.status == "success") {
            return response.data
        } else {
            throw Exception("Error al obtener personal: ${response.status}")
        }
    }
}

