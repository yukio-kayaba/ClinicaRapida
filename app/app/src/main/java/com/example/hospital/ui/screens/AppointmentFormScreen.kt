package com.example.hospital.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hospital.navigation.Screen
import com.example.hospital.ui.theme.TextSecondary
import com.example.hospital.ui.theme.TopBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.hospital.data.model.ReservacionRequest
import com.example.hospital.data.repository.ReservacionRepository

@Composable
fun AppointmentFormScreen(
    doctorId: Int,
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    var dni by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var dniError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var correoError by remember { mutableStateOf<String?>(null) }
    
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Reservar Cita",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // DNI Field
        OutlinedTextField(
            value = dni,
            onValueChange = { 
                dni = it.filter { char -> char.isDigit() }
                dniError = null // Limpiar error al escribir
                errorMessage = null // Limpiar error general también
            },
            label = { Text("DNI") },
            placeholder = { Text("8 dígitos", color = TextSecondary) },
            isError = dniError != null,
            supportingText = dniError?.let { { Text(it, color = androidx.compose.ui.graphics.Color(0xFFD32F2F)) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                errorContainerColor = androidx.compose.ui.graphics.Color(0xFFFFEBEE)
            ),
            singleLine = true
        )
        
        // Nombre Field
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
            ),
            singleLine = true
        )
        
        // Apellido Field
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
            ),
            singleLine = true
        )
        
        // Teléfono Field (9 dígitos, solo números - Perú)
        OutlinedTextField(
            value = telefono,
            onValueChange = {
                telefono = it.filter { char -> char.isDigit() }.take(9)
                telefonoError = null
                errorMessage = null
            },
            label = { Text("Teléfono") },
            placeholder = { Text("9 dígitos (Perú)", color = TextSecondary) },
            isError = telefonoError != null,
            supportingText = telefonoError?.let { { Text(it, color = androidx.compose.ui.graphics.Color(0xFFD32F2F)) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                errorContainerColor = androidx.compose.ui.graphics.Color(0xFFFFEBEE)
            ),
            singleLine = true
        )
        
        // Correo electrónico Field (debe contener @)
        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                correoError = null
                errorMessage = null
            },
            label = { Text("Correo electrónico") },
            placeholder = { Text("ejemplo@correo.com", color = TextSecondary) },
            isError = correoError != null,
            supportingText = correoError?.let { { Text(it, color = androidx.compose.ui.graphics.Color(0xFFD32F2F)) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                errorContainerColor = androidx.compose.ui.graphics.Color(0xFFFFEBEE)
            ),
            singleLine = true
        )
        
        // Fecha Field (DatePicker)
        OutlinedTextField(
            value = fecha,
            onValueChange = { },
            readOnly = true,
            label = { Text("Fecha de cita") },
            placeholder = { Text("Ej: 25/12/2024", color = TextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
            ),
            singleLine = true,
            enabled = !isLoading && !showSuccessMessage
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Seleccionar fecha",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .clickable(enabled = !isLoading && !showSuccessMessage) {
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    
                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            val mm = (m + 1).toString().padStart(2, '0')
                            val dd = d.toString().padStart(2, '0')
                            fecha = "$dd/$mm/$y"
                        },
                        year,
                        month,
                        day
                    ).show()
                }
        )
        
        // Hora Field (TimePickerDialog)
        OutlinedTextField(
            value = hora,
            onValueChange = { },
            readOnly = true,
            label = { Text("Hora de cita") },
            placeholder = { Text("Ej: 14:30", color = TextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
            ),
            singleLine = true,
            enabled = !isLoading && !showSuccessMessage
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Seleccionar hora",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clickable(enabled = !isLoading && !showSuccessMessage) {
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)
                    
                    TimePickerDialog(
                        context,
                        { _, h, min ->
                            val hh = h.toString().padStart(2, '0')
                            val mm = min.toString().padStart(2, '0')
                            hora = "$hh:$mm"
                        },
                        hour,
                        minute,
                        true
                    ).show()
                }
        )

        // Motivo Field
        OutlinedTextField(
            value = motivo,
            onValueChange = { motivo = it },
            label = { Text("Motivo") },
            placeholder = { Text("Ej: Consulta general", color = TextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
            ),
            minLines = 3
        )
        
        // Error Message
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color(0xFFD32F2F),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        
        // Success Message
        if (showSuccessMessage) {
            Text(
                text = "Solicitud enviada. Recibirá un correo cuando su cita sea aprobada o rechazada.",
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color(0xFF4CAF50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        
        // Submit Button
        Button(
            onClick = {
                if (!isLoading) {
                    // Limpiar errores previos
                    errorMessage = null
                    dniError = null
                    telefonoError = null
                    correoError = null
                    
                    // Validar DNI antes de enviar
                    val dniClean = dni.trim()
                    if (dniClean.length != 8 || !dniClean.all { it.isDigit() }) {
                        dniError = "El DNI debe tener exactamente 8 dígitos"
                        return@Button
                    }
                    
                    // Validar campos requeridos
                    if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || 
                        correo.trim().isEmpty() || telefono.trim().isEmpty() ||
                        fecha.isEmpty() || hora.isEmpty() || motivo.trim().isEmpty()) {
                        errorMessage = "Por favor completa todos los campos"
                        return@Button
                    }
                    
                    // Validar teléfono (9 dígitos)
                    val telefonoClean = telefono.trim()
                    if (telefonoClean.length != 9 || !telefonoClean.all { it.isDigit() }) {
                        telefonoError = "El teléfono debe tener exactamente 9 dígitos (número peruano)"
                        return@Button
                    }
                    
                    // Validar correo (debe contener @)
                    val correoClean = correo.trim()
                    if (!correoClean.contains("@")) {
                        correoError = "El correo debe contener un @ válido"
                        return@Button
                    }
                    
                    isLoading = true
                    scope.launch {
                        try {
                            val request = ReservacionRequest(
                                nombre = nombre.trim(),
                                apellido = apellido.trim(),
                                correo = correoClean,
                                dni = dniClean,
                                fecha = fecha,
                                hora = hora,
                                idpersonalacceso = doctorId,
                                motivo = motivo.trim(),
                                telefono = telefonoClean
                            )
                            
                            ReservacionRepository.crearReservacion(request)
                            
                            isLoading = false
                            showSuccessMessage = true
                            errorMessage = null
                            
                            // Wait a moment to show the success message, then navigate
                            delay(2000)
                            navController?.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            isLoading = false
                            // Mostrar el mensaje de error del backend o genérico
                            errorMessage = e.message ?: "Error al crear la reservación. Intenta nuevamente."
                            e.printStackTrace()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading && !showSuccessMessage && errorMessage == null
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = androidx.compose.ui.graphics.Color.White
                )
            } else {
                Text("Enviar solicitud")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

