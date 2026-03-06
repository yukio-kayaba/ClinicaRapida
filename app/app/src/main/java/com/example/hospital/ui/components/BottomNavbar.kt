package com.example.hospital.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.hospital.navigation.Screen

sealed class BottomNavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    object Home : BottomNavItem("Home", Icons.Default.Home, Screen.Home.route)
    object Buscar : BottomNavItem("Buscar", Icons.Default.Search, Screen.Buscar.route)
    object Servicios : BottomNavItem("Servicios", Icons.Default.LocalHospital, Screen.Servicios.route)
}

@Composable
fun BottomNavbar(
    currentRoute: String?,
    onItemSelected: (String) -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Buscar,
        BottomNavItem.Servicios
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    onItemSelected(item.route)
                }
            )
        }
    }
}

