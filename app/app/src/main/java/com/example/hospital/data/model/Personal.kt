package com.example.hospital.data.model

data class Personal(
    val nombre: String,
    val apellido: String,
    val dni: String,
    val idpersona: Int,
    val descripcionpersonal: String?,
    val profesion: List<ProfesionPersonal>,
    val telefono: List<TelefonoPersonal>,
    val correo: List<CorreoPersonal>,
    val fotos: List<FotoPersonal>
)

