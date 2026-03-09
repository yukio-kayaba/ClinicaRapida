package com.example.hospital.core.util

/**
 * Normaliza números peruanos para usarlos en un URI tel:.
 *
 * - Quita todo lo que no sea dígito, excepto un '+' inicial.
 * - Si viene como 9 dígitos y empieza con '9' => lo convierte a +51XXXXXXXXX
 * - Si viene como 11 dígitos y empieza con '51' => lo convierte a +51XXXXXXXXX
 */
object PhoneUtils {
    fun toPeruE164(raw: String?): String? {
        val input = raw?.trim().orEmpty()
        if (input.isEmpty()) return null

        // Mantener '+' si está al inicio; luego solo dígitos
        val hasPlus = input.startsWith("+")
        val digitsOnly = input.filter { it.isDigit() }
        if (digitsOnly.isEmpty()) return null

        val normalizedDigits = when {
            digitsOnly.length == 9 && digitsOnly.startsWith("9") -> "51$digitsOnly"
            digitsOnly.length == 11 && digitsOnly.startsWith("51") -> digitsOnly
            else -> digitsOnly // fallback
        }

        return if (hasPlus || normalizedDigits.startsWith("51")) "+$normalizedDigits" else "+$normalizedDigits"
    }
}


