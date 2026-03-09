package com.example.hospital.core.network

import android.content.Context
import coil.ImageLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Coil ImageLoader que usa OkHttp con AuthInterceptor, para que las imágenes protegidas
 * por header "z-project-token" puedan descargarse correctamente.
 */
object CoilImageLoader {
    @Volatile
    private var cached: ImageLoader? = null

    fun get(context: Context): ImageLoader {
        return cached ?: synchronized(this) {
            cached ?: build(context.applicationContext).also { cached = it }
        }
    }

    private fun build(appContext: Context): ImageLoader {
        val logging = HttpLoggingInterceptor().apply {
            // Cambia a NONE si no quieres logs de imágenes
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logging)
            .build()

        return ImageLoader.Builder(appContext)
            .okHttpClient(okHttpClient)
            .build()
    }
}


