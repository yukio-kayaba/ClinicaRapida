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
            onValueChange = { dni = it },
            label = { Text("DNI") },
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
        
        // Teléfono Field
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
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
        
        // Correo electrónico Field
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
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
                    isLoading = true
                    scope.launch {
                        val request = ReservacionRequest(
                            nombre = nombre,
                            apellido = apellido,
                            correo = correo,
                            dni = dni,
                            fecha = fecha,
                            hora = hora,
                            idpersonalacceso = doctorId,
                            motivo = motivo,
                            telefono = telefono
                        )
                        
                        ReservacionRepository.crearReservacion(request)
                        
                        isLoading = false
                        showSuccessMessage = true
                        
                        // Wait a moment to show the success message, then navigate
                        delay(2000)
                        navController?.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading && !showSuccessMessage
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

