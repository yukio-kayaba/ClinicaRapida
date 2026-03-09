package com.example.hospital.core.session

import com.example.hospital.core.network.ApiConfig

object TokenManager {
    var tokenAcceso: String? = null

    fun getToken(): String {
        val raw = (tokenAcceso ?: ApiConfig.BASIC_TOKEN).trim()
        // El backend exige: "z " + TOKEN (si ya viene con "z ", lo respetamos)
        return if (raw.startsWith("z ", ignoreCase = true)) raw else "z $raw"
    }
}

