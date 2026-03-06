package com.example.hospital.data.model

data class AppointmentRequest(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val correo: String,
    val doctorId: Int,
    val fecha: String,
    val hora: String
)

