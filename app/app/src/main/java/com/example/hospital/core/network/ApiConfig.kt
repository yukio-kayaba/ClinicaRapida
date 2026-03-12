package com.example.hospital.core.network

object ApiConfig {
    const val BASE_URL = "http://10.0.2.2:4000/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJlbmZlcm1lcmlhRGF0YSIsImlkUHJveWVjdCI6MSwiaWF0IjoxNzczMjY3OTUwfQ.I0yI26-MScuKPQOpN1_ROow5nX65_JW9EHAjhoCqfrE"
    const val BASIC_TOKEN = "z "+TOKEN
    
    fun buildImageUrl(path: String): String {
        return BASE_URL + path.removePrefix("/")
    }
}

