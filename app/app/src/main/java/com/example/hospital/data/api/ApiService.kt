package com.example.hospital.data.api

import com.example.hospital.data.model.PersonalResponse
import com.example.hospital.data.model.ProyectoResponse
import com.example.hospital.data.model.ReservacionRequest
import com.example.hospital.data.model.ServiciosResponse
import com.example.hospital.data.model.SimpleStatusResponse
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("api/centromedico/servicios/proyecto")
    suspend fun obtenerProyecto(): ProyectoResponse
    
    @GET("/api/centromedico/personal")
    suspend fun obtenerPersonal(): PersonalResponse

    @POST("/api/centromedico/reservaciones")
    suspend fun crearReservacion(
        @Body request: ReservacionRequest
    ): SimpleStatusResponse

    @GET("/api/centromedico/servicios")
    suspend fun obtenerServicios(): ServiciosResponse
}

