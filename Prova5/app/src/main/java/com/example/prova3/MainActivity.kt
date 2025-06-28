package com.example.prova3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova3.model.CommunicationController
import com.example.prova3.model.LocationManager
import com.example.prova3.model.Storage
import com.example.prova3.repository.GestioneAccountRepository
import com.example.prova3.repository.GestioneMenuRepository
import com.example.prova3.repository.GestioneOrdiniRepository
import com.example.prova3.screens.EditProfileCard
import com.example.prova3.screens.Homepage
import com.example.prova3.screens.Profile
import com.example.prova3.screens.EditProfileData
import com.example.prova3.screens.FirstScreen
import com.example.prova3.screens.LoadingScreen
import com.example.prova3.screens.MenuDetail


class MainActivity : ComponentActivity() {

    // Launcher per richiedere i permessi
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Permesso fine location concesso
                println("Permesso FINE_LOCATION concesso")
            }
            else -> {
                // Nessun permesso concesso
                println("Nessun permesso di posizione concesso")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inizializza i singleton
        Storage.initialize(this)
        LocationManager.initialize(this)
        // Controlla e richiedi i permessi
        checkAndRequestLocationPermissions()

        val gestioneMenuRepository : GestioneMenuRepository = GestioneMenuRepository()
        val gestioneAccountRepository : GestioneAccountRepository = GestioneAccountRepository()
        val gestioneOrdiniRepository : GestioneOrdiniRepository = GestioneOrdiniRepository()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController, "LoadingScreen") {
                composable(route = "FirstScreen") {
                    FirstScreen(navController, gestioneAccountRepository)
                }
                composable( route = "Homepage"){
                    Homepage(navController, gestioneMenuRepository)
                }

                composable( route = "Profile"){
                    Profile(navController, gestioneAccountRepository, gestioneOrdiniRepository)
                }

                composable( route = "EditProfileData") {
                    EditProfileData(navController, gestioneAccountRepository)
                }
                composable( route = "EditProfileCard") {
                    EditProfileCard(navController, gestioneAccountRepository)
                }
                composable(route = "MenuDetail") {
                    MenuDetail(navController, gestioneMenuRepository,56)
                }
                composable(route = "LoadingScreen") {
                    LoadingScreen(navController)
                }
            }
        }
    }

    private fun checkAndRequestLocationPermissions() {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED ) {
            // Richiedi i permessi
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )
        }
    }
}