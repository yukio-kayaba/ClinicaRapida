package com.example.hospital.core.network

object ApiConfig {
    // Debe terminar en "/" para Retrofit y para construir bien las URLs de imagen
    const val BASE_URL =  "https://server.zynova.online/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJDZW50cm9NZWRpY28iLCJpZFByb3llY3QiOjEsImlhdCI6MTc3MzU5MzQ1NH0.Iap7Gr8qJX82A0CddOwRHD8vwiFmIPMgK8ggntAm-XQ"
    const val BASIC_TOKEN = "z "+TOKEN
    
    fun buildImageUrl(path: String): String {
        // Asegura que siempre haya exactamente una "/" entre BASE_URL y el path
        val base = BASE_URL.trimEnd('/')
        val cleanPath = path.removePrefix("/")
        return "$base/$cleanPath"
    }
}

