package com.example.hospital.data.model

/**
 * Respuesta genérica para endpoints que devuelven solo status/message.
 * Ajusta campos si tu backend devuelve algo diferente.
 */
data class SimpleStatusResponse(
    val status: String,
    val message: String? = null
)


