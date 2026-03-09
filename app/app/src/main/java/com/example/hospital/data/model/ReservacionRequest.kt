package com.example.hospital.data.model

data class ReservacionRequest(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val dni: String,
    val fecha: String,
    val hora: String,
    val idpersonalacceso: Int,
    val motivo: String,
    val telefono: String
)


