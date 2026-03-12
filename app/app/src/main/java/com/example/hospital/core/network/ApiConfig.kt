package com.example.hospital.core.network

object ApiConfig {
    const val BASE_URL = "https://server.zynova.online/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJlbmZlcm1lcmlhRGF0YSIsImlkUHJveWVjdCI6MSwiaWF0IjoxNzczMjkyMDIwfQ.h7rJMmRRQG4_A1raWMoytCuDL2GfKfDobVlIs1iqrTY"
    const val BASIC_TOKEN = "z "+TOKEN
    
    fun buildImageUrl(path: String): String {
        return BASE_URL + path.removePrefix("/")
    }
}

