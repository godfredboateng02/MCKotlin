package com.example.prova3

import EditProfileCard
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prova3.model.LocationManager
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneMenuRepository
import com.example.prova3.model.repository.GestioneOrdiniRepository
import com.example.prova3.screens.Delivery
import com.example.prova3.screens.EditProfileData
import com.example.prova3.screens.FirstScreen
import com.example.prova3.screens.Homepage
import com.example.prova3.screens.LoadingScreen
import com.example.prova3.screens.MenuDetail
import com.example.prova3.screens.PageOfShame
import com.example.prova3.screens.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

            // Osserva la route corrente e salvala nel companion object
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            currentRoute = navBackStackEntry?.destination?.route

            // Salva anche il mid corrente se siamo in MenuDetail
            navBackStackEntry?.arguments?.getInt("mid")?.let { mid ->
                if (navBackStackEntry!!.destination.route == "MenuDetail/{mid}") {
                    currentMid = mid
                }
            }

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
                composable(
                    route = "MenuDetail/{mid}",
                    arguments = listOf(navArgument("mid") { type = NavType.IntType })
                ) { backStackEntry ->
                    val mid = backStackEntry.arguments?.getInt("mid") ?: 0
                    MenuDetail(navController, gestioneMenuRepository, gestioneAccountRepository,gestioneOrdiniRepository, mid)
                }
                composable(route = "LoadingScreen") {
                    LoadingScreen(navController)
                }

                composable (route = "Delivery"){
                    Delivery(navController, gestioneOrdiniRepository)
                }
                composable(route = "PageOfShame"){
                    PageOfShame(navController)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        // Salva la route corrente quando l'app va in pausa
        CoroutineScope(Dispatchers.IO).launch {
            currentRoute?.let { route ->
                when {
                    route == "PageOfShame" -> {
                        // Non salvare PageOfShame
                    }
                    route == "MenuDetail/{mid}" && currentMid != null -> {
                        // Salva MenuDetail con il mid concreto
                        Storage.setPagina("MenuDetail/$currentMid")
                        println("Route salvata in onPause: MenuDetail/$currentMid")
                    }
                    else -> {
                        Storage.setPagina(route)
                        println("Route salvata in onPause: $route")
                    }
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

    companion object {
        // Variabile statica per tenere traccia della route corrente
        var currentRoute: String? = null
        var currentMid: Int? = null
    }
}