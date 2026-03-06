package com.example.hospital.data.model

data class Doctor(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val dni: String,
    val telefono: List<Telefono>,
    val profesiones: List<Profesion>,
    val fotos: List<String>
)

