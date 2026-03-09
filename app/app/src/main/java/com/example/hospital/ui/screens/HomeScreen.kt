package com.example.hospital.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hospital.core.session.TokenManager
import com.example.hospital.data.fake.FakeStaffRepository
import com.example.hospital.data.model.Staff
import com.example.hospital.data.repository.PersonalMapper
import com.example.hospital.data.repository.PersonalRepository
import com.example.hospital.ui.components.AppLoader
import com.example.hospital.ui.components.StaffCard
import com.example.hospital.ui.components.StaffDetailModal
import com.example.hospital.ui.theme.TextSecondary
import com.example.hospital.ui.theme.TopBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    // State management
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var selectedStaff by remember { mutableStateOf<Staff?>(null) }
    var showModal by remember { mutableStateOf(false) }
    var allStaff by remember { mutableStateOf<List<Staff>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    
    // Cargar datos del backend cuando el token esté disponible
    LaunchedEffect(Unit) {
        if (TokenManager.tokenAcceso != null || TokenManager.getToken() != "z tokenSecreto") {
            try {
                val personal = withContext(Dispatchers.IO) {
                    PersonalRepository.obtenerPersonal()
                }
                allStaff = PersonalMapper.mapToStaffList(personal)
                isLoading = false
            } catch (e: Exception) {
                e.printStackTrace()
                // Si falla, usar datos falsos
                allStaff = FakeStaffRepository.getStaff()
                hasError = true
                isLoading = false
            }
        } else {
            // Si no hay token, usar datos falsos
            allStaff = FakeStaffRepository.getStaff()
            isLoading = false
        }
    }
    
    // Filtros dinámicos (no repetidos) en base a profesiones del backend
    val professionFilters = remember(allStaff) {
        val unique = allStaff
            .flatMap { staff -> staff.profesiones.ifEmpty { listOf(staff.profesion) } }
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
        listOf("Todos") + unique
    }

    // Filter staff based on search and profession filter
    val filteredStaff = remember(searchQuery, selectedFilter, allStaff) {
        allStaff.filter { staff ->
            // Search filter (by name or specialty)
            val matchesSearch = searchQuery.isEmpty() ||
                staff.nombre.contains(searchQuery, ignoreCase = true) ||
                staff.especialidad.contains(searchQuery, ignoreCase = true)

            // Profession filter (dinámico por nombreprofesion)
            val matchesFilter = if (selectedFilter == "Todos") {
                true
            } else {
                val professions = staff.profesiones.ifEmpty { listOf(staff.profesion) }
                professions.any { it.equals(selectedFilter, ignoreCase = true) }
            }

            matchesSearch && matchesFilter
        }
    }
    
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AppLoader()
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
        // Search Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = androidx.compose.ui.graphics.Color.White
            )
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Buscar personal...",
                        color = TextSecondary
                    )
                },
                leadingIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
                ),
                singleLine = true
            )
        }
        
        // Filter Chips - Horizontal Scrollable (dinámicos)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(professionFilters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TopBlue,
                        selectedLabelColor = androidx.compose.ui.graphics.Color.White,
                        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                    )
                )
            }
        }
        
        // Staff List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(
                items = filteredStaff,
                key = { _, staff -> staff.id }
            ) { index, staff ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(300, delayMillis = index * 50)
                    ) + slideInVertically(
                        animationSpec = tween(300, delayMillis = index * 50),
                        initialOffsetY = { it / 2 }
                    )
                ) {
                    StaffCard(
                        staff = staff,
                        onClick = {
                            selectedStaff = staff
                            showModal = true
                        }
                    )
                }
            }
        }
        }
    }
    
    // Show Modal
    if (showModal && selectedStaff != null) {
        StaffDetailModal(
            staff = selectedStaff!!,
            onDismiss = {
                showModal = false
                selectedStaff = null
            },
            navController = navController
        )
    }
}
