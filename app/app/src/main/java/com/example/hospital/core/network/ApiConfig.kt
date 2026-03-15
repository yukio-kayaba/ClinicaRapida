package com.example.hospital.core.network

object ApiConfig {
    const val BASE_URL = "https://server.zynova.online/"
    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJDZW50cm9NZWRpY28iLCJpZFByb3llY3QiOjEsImlhdCI6MTc3MzQzNTkzNH0.iLQJmOE0XA8lFtcdRGdc935liaUHwBI0gVJW3e2U3w8"
    const val BASIC_TOKEN = "z "+TOKEN
    
    fun buildImageUrl(path: String): String {
        return BASE_URL + path.removePrefix("/")
    }
}

