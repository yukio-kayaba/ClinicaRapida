package com.example.hospital.core.network

object ApiConfig {
    const val BASE_URL = "http://10.0.2.2:4000/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJlbmZlcm1lcmlhRGF0YSIsImlkUHJveWVjdCI6MSwiaWF0IjoxNzczMDE1ODQzfQ.Xsqf7VBD1vAY6yEEeJxaW7_4vz5u8wZSfO-2Gp9IRn8"
    const val BASIC_TOKEN = "z "+TOKEN
    
    fun buildImageUrl(path: String): String {
        return BASE_URL + path.removePrefix("/")
    }
}

