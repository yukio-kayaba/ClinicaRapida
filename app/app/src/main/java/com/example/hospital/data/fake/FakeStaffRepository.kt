package com.example.hospital.data.fake

import com.example.hospital.data.model.Staff

object FakeStaffRepository {
    fun getStaff(): List<Staff> {
        return listOf(
            Staff(
                id = 1,
                nombre = "Laura Sánchez",
                especialidad = "Ginecología y Obstetricia",
                profesion = "Obstetra",
                experiencia = 10,
                descripcion = "Especialista en atención prenatal y partos",
                imagenes = listOf(
                    "https://placeholder.com/200/2196F3/FFFFFF?text=LS"
                ),
                telefono = "51987654321"
            ),
            Staff(
                id = 2,
                nombre = "Carlos Ramirez",
                especialidad = "Cuidados Intensivos",
                profesion = "Enfermero",
                experiencia = 8,
                descripcion = "Experto en cuidados críticos y emergencias",
                imagenes = listOf(
                    "https://placeholder.com/200/4CAF50/FFFFFF?text=CR"
                ),
                telefono = "51987654322"
            ),
            Staff(
                id = 3,
                nombre = "Juan Perez",
                especialidad = "Cardiología",
                profesion = "Medico",
                experiencia = 12,
                descripcion = "Cardiólogo con amplia experiencia en diagnóstico y tratamiento",
                imagenes = listOf(
                    "https://placeholder.com/200/FF9800/FFFFFF?text=JP"
                ),
                telefono = "51987654323"
            )
        )
    }
}

