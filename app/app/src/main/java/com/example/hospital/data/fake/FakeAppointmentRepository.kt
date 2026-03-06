package com.example.hospital.data.fake

import com.example.hospital.data.model.AppointmentRequest
import kotlinx.coroutines.delay

object FakeAppointmentRepository {
    suspend fun sendAppointmentRequest(request: AppointmentRequest): Result<Unit> {
        // Simulate network delay
        delay(2000)
        
        // Simulate successful response
        return Result.success(Unit)
    }
}

