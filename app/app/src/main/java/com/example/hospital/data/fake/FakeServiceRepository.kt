package com.example.hospital.data.fake

import com.example.hospital.data.model.Service
import kotlinx.coroutines.delay

object FakeServiceRepository {
    suspend fun getServices(): List<Service> {
        // Simulate network delay
        delay(1000)
        
        return listOf(
            Service(
                id = 1,
                titulo = "Cardiología",
                descripcion = "Servicio especializado en el diagnóstico y tratamiento de enfermedades del corazón y del sistema circulatorio."
            ),
            Service(
                id = 2,
                titulo = "Pediatría",
                descripcion = "Atención médica integral para niños y adolescentes desde el nacimiento hasta los 18 años."
            ),
            Service(
                id = 3,
                titulo = "Medicina general",
                descripcion = "Atención primaria de salud para pacientes de todas las edades con enfoque en prevención y tratamiento."
            ),
            Service(
                id = 4,
                titulo = "Enfermería especializada",
                descripcion = "Cuidados de enfermería avanzados con personal altamente capacitado en diversas especialidades."
            ),
            Service(
                id = 5,
                titulo = "Laboratorio clínico",
                descripcion = "Análisis clínicos y estudios de laboratorio para diagnóstico preciso y seguimiento de tratamientos."
            )
        )
    }
}

