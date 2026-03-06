package com.example.hospital.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hospital.data.fake.FakeStaffRepository
import com.example.hospital.data.model.Staff
import com.example.hospital.ui.components.StaffCard
import com.example.hospital.ui.components.StaffDetailModal
import com.example.hospital.ui.theme.TextSecondary
import com.example.hospital.ui.theme.TopBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    val allStaff = FakeStaffRepository.getStaff()
    
    // State management
    var searchQuery by remember { mutableStateOf("") }
    var selectedStaff by remember { mutableStateOf<Staff?>(null) }
    var showModal by remember { mutableStateOf(false) }
    
    // Filter staff based on search query (real-time)
    val filteredStaff = remember(searchQuery, allStaff) {
        if (searchQuery.isEmpty()) {
            allStaff
        } else {
            allStaff.filter { staff ->
                staff.nombre.contains(searchQuery, ignoreCase = true) ||
                staff.especialidad.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
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
                        text = "Buscar por nombre o especialidad...",
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
        
        // Staff List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            if (filteredStaff.isEmpty() && searchQuery.isNotEmpty()) {
                item {
                    Text(
                        text = "No se encontraron resultados",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = TextSecondary
                    )
                }
            } else {
                items(filteredStaff) { staff ->
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
