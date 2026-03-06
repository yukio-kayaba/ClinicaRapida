package com.example.hospital.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hospital.ui.screens.AppointmentFormScreen
import com.example.hospital.ui.screens.HomeScreen
import com.example.hospital.ui.screens.SearchScreen
import com.example.hospital.ui.screens.ServicesScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Buscar : Screen("buscar")
    object Servicios : Screen("servicios")
    object AppointmentForm : Screen("appointment_form/{doctorId}") {
        fun createRoute(doctorId: Int) = "appointment_form/$doctorId"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Buscar.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.Servicios.route) {
            ServicesScreen()
        }
        composable(
            route = Screen.AppointmentForm.route,
            arguments = listOf(
                navArgument("doctorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            AppointmentFormScreen(doctorId = doctorId, navController = navController)
        }
    }
}

