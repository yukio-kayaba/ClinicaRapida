package com.example.hospital.data.model

data class PersonalResponse(
    val status: String,
    // El backend devuelve: "data": [ { ... }, { ... } ]
    val data: List<Personal>
)

