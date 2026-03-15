package com.example.hospital.data.repository

import com.example.hospital.core.network.ApiConfig
import com.example.hospital.data.model.Personal
import com.example.hospital.data.model.Staff

object PersonalMapper {
    fun mapToStaff(personal: Personal): Staff {
        val nombreCompleto = "${personal.nombre} ${personal.apellido}"
        val profesiones = personal.profesion
            .map { it.nombreprofesion.trim() }
            .filter { it.isNotEmpty() }
            .distinct()

        val profesionNombre = profesiones.firstOrNull() ?: "Profesional"
        val telefonoPrincipal = personal.telefono.firstOrNull { it.principal }?.numeroCel 
            ?: personal.telefono.firstOrNull()?.numeroCel 
            ?: ""
        
        // Calcular años de experiencia desde la primera profesión
        val anioInicio = personal.profesion.firstOrNull()?.anioInicio?.toIntOrNull() ?: 0
        val anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val experiencia = if (anioInicio > 0) anioActual - anioInicio else 0
        
        // Mapear fotos a URLs completas usando buildImageUrl
        val fotosUrls = personal.fotos.map { foto ->
            if (foto.filename.startsWith("http://") || foto.filename.startsWith("https://")) {
                foto.filename
            } else {
                ApiConfig.buildImageUrl(foto.filename)
            }
        }
        
        return Staff(
            id = personal.idpersona,
            nombre = nombreCompleto,
            especialidad = profesionNombre,
            profesion = profesionNombre,
            profesiones = profesiones,
            experiencia = experiencia,
            descripcion = personal.descripcionpersonal ?: "",
            imagenes = fotosUrls,
            telefono = telefonoPrincipal
        )
    }
    
    fun mapToStaffList(personalList: List<Personal>): List<Staff> {
        return personalList.map { mapToStaff(it) }
    }
}

