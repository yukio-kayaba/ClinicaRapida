package com.example.hospital.data.api

import com.example.hospital.data.model.PersonalResponse
import com.example.hospital.data.model.ProyectoResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/proyecto")
    suspend fun obtenerProyecto(): ProyectoResponse
    
    @GET("/api/centromedico/personal")
    suspend fun obtenerPersonal(): PersonalResponse
}

