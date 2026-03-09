package com.example.hospital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hospital.core.session.ProjectInitializer
import com.example.hospital.core.session.ProjectManager
import com.example.hospital.navigation.AppNavigation
import com.example.hospital.ui.components.BottomNavbar
import com.example.hospital.ui.components.HeaderBar
import com.example.hospital.ui.theme.HospitalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inicializar proyecto cuando la app inicia
        lifecycleScope.launch {
            ProjectInitializer.initialize(this@MainActivity, this) { success ->
                if (success) {
                    // Proyecto inicializado correctamente
                }
            }
        }
        
        setContent {
            HospitalTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                var nombreProyecto by remember { mutableStateOf(ProjectManager.nombreProyecto) }
                
                // Inicializar proyecto (backup en caso de que no se haya inicializado)
                LaunchedEffect(Unit) {
                    ProjectInitializer.initialize(context, scope) { success ->
                        nombreProyecto = ProjectManager.nombreProyecto
                    }
                }
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        HeaderBar(nombreProyecto = nombreProyecto)
                    },
                    bottomBar = {
                        BottomNavbar(
                            currentRoute = currentRoute,
                            onItemSelected = { route ->
                                navController.navigate(route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        val context = LocalContext.current
                        FloatingActionButton(
                            onClick = {
                                val phoneNumber = ProjectManager.telefono ?: "+51987654321"
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Llamar al hospital"
                            )
                        }
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HospitalTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        Scaffold(
            topBar = {
                HeaderBar()
            },
            bottomBar = {
                BottomNavbar(
                    currentRoute = currentRoute,
                    onItemSelected = { }
                )
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}