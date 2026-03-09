package com.example.hospital.core.session

import android.content.Context
import android.content.SharedPreferences

object LocalStorageManager {
    private const val PREFS_NAME = "hospital_prefs"
    private const val KEY_TOKEN_ACCESO = "token_acceso"
    private const val KEY_TELEFONO = "telefono"
    private const val KEY_NOMBRE_PROYECTO = "nombre_proyecto"
    
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveTokenAcceso(context: Context, token: String) {
        getSharedPreferences(context).edit()
            .putString(KEY_TOKEN_ACCESO, token)
            .apply()
    }
    
    fun getTokenAcceso(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN_ACCESO, null)
    }
    
    fun saveTelefono(context: Context, telefono: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_TELEFONO, telefono)
            .apply()
    }
    
    fun getTelefono(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TELEFONO, null)
    }
    
    fun saveNombreProyecto(context: Context, nombre: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_NOMBRE_PROYECTO, nombre)
            .apply()
    }
    
    fun getNombreProyecto(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_NOMBRE_PROYECTO, null)
    }
    
    fun loadLocalData(context: Context) {
        val prefs = getSharedPreferences(context)
        TokenManager.tokenAcceso = prefs.getString(KEY_TOKEN_ACCESO, null)
        ProjectManager.telefono = prefs.getString(KEY_TELEFONO, null)
        ProjectManager.nombreProyecto = prefs.getString(KEY_NOMBRE_PROYECTO, null)
    }
    
    fun saveAllData(context: Context, token: String?, telefono: String?, nombre: String?) {
        if (token != null) {
            saveTokenAcceso(context, token)
        }
        if (telefono != null) {
            saveTelefono(context, telefono)
        }
        if (nombre != null) {
            saveNombreProyecto(context, nombre)
        }
    }
}

