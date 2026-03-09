package com.example.hospital.core.network

import com.example.hospital.core.session.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val requestWithToken = originalRequest.newBuilder()
            .header("z-project-token", TokenManager.getToken())
            .build()
        
        return chain.proceed(requestWithToken)
    }
}

