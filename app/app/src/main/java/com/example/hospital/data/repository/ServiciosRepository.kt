package com.example.hospital.data.repository

import com.example.hospital.core.network.RetrofitClient
import com.example.hospital.data.model.Service

object ServiciosRepository {
    suspend fun obtenerServicios(): List<Service> {
        val response = RetrofitClient.api.obtenerServicios()
        if (response.status != "success") {
            throw Exception("Error al obtener servicios: ${response.status}")
        }

        return response.data.mapIndexed { index, s ->
            Service(
                id = index + 1,
                titulo = s.servicio,
                descripcion = s.descripcion
            )
        }
    }
}


