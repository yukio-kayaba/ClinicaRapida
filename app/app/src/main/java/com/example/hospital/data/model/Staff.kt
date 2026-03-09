package com.example.hospital.data.model

data class Staff(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val profesion: String, // Medico, Enfermera, Obstetra
    val profesiones: List<String>,
    val experiencia: Int,
    val descripcion: String,
    val imagenes: List<String>,
    val telefono: String
)

