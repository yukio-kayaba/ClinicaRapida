package com.example.hospital.data.api

import com.example.hospital.data.model.Doctor
import com.example.hospital.data.model.Profesion
import com.example.hospital.data.model.Telefono

object FakeApiService {
    fun obtenerDoctores(): List<Doctor> {
        return listOf(
            Doctor(
                nombre = "Juan",
                apellido = "Pérez",
                edad = 45,
                dni = "12345678",
                telefono = listOf(
                    Telefono(numeroCelular = "51987654321"),
                    Telefono(numeroCelular = "51987654322")
                ),
                profesiones = listOf(
                    Profesion(
                        nombreProfesion = "Cardiólogo",
                        anioInicio = "2010",
                        anioFinalizacion = "2015"
                    ),
                    Profesion(
                        nombreProfesion = "Médico General",
                        anioInicio = "2005",
                        anioFinalizacion = "2010"
                    )
                ),
                fotos = listOf(
                    "https://i.pravatar.cc/400?img=1",
                    "https://i.pravatar.cc/400?img=2"
                )
            ),
            Doctor(
                nombre = "María",
                apellido = "González",
                edad = 38,
                dni = "87654321",
                telefono = listOf(
                    Telefono(numeroCelular = "51987654323")
                ),
                profesiones = listOf(
                    Profesion(
                        nombreProfesion = "Pediatra",
                        anioInicio = "2012",
                        anioFinalizacion = "2017"
                    )
                ),
                fotos = listOf(
                    "https://i.pravatar.cc/400?img=3",
                    "https://i.pravatar.cc/400?img=4"
                )
            ),
            Doctor(
                nombre = "Carlos",
                apellido = "Rodríguez",
                edad = 42,
                dni = "11223344",
                telefono = listOf(
                    Telefono(numeroCelular = "51987654324"),
                    Telefono(numeroCelular = "51987654325"),
                    Telefono(numeroCelular = "51987654326")
                ),
                profesiones = listOf(
                    Profesion(
                        nombreProfesion = "Cirujano General",
                        anioInicio = "2008",
                        anioFinalizacion = "2013"
                    ),
                    Profesion(
                        nombreProfesion = "Traumatólogo",
                        anioInicio = "2013",
                        anioFinalizacion = "2018"
                    )
                ),
                fotos = listOf(
                    "https://i.pravatar.cc/400?img=5",
                    "https://i.pravatar.cc/400?img=6",
                    "https://i.pravatar.cc/400?img=7"
                )
            )
        )
    }
}

