package com.example.hospital.core.session

import android.content.Context
import com.example.hospital.core.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ProjectInitializer {
    fun initialize(context: Context, scope: CoroutineScope, onComplete: (Boolean) -> Unit = {}) {
        // Primero cargar datos locales
        LocalStorageManager.loadLocalData(context)
        
        scope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerProyecto()
                }
                
                if (response.status == "success" && response.data.isNotEmpty()) {
                    val proyecto = response.data[0]
                    
                    // Guardar token de acceso
                    TokenManager.tokenAcceso = "z "+proyecto.tokenAcceso
                    
                    // Guardar datos del proyecto
                    ProjectManager.telefono = proyecto.telefono
                    ProjectManager.nombreProyecto = proyecto.nombre
                    
                    // Guardar en almacenamiento local
                    LocalStorageManager.saveAllData(
                        context,
                        proyecto.tokenAcceso,
                        proyecto.telefono,
                        proyecto.nombre
                    )
                    
                    onComplete(true)
                } else {
                    // Si falla, usar datos locales ya cargados
                    onComplete(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Si falla, usar datos locales ya cargados
                onComplete(false)
            }
        }
    }
}

