package com.example.prova3.screens

import android.health.connect.datatypes.ExerciseRoute.Location
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.prova3.model.LocationManager
import com.example.prova3.model.Storage
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController){
    BackHandler {
        // Disabilita il back button
    }

    LaunchedEffect(Unit) {
        // Breve pausa per lo spinning wheel
        delay(70)

        val pagina = Storage.getPagina()

        // Gestione sicura della navigazione
        val destinazione = when {
            // Se non abbiamo i permessi e non siamo in FirstScreen
            !LocationManager.hasLocationPermission() && pagina != "FirstScreen" -> "PageOfShame"

            // Altrimenti usa la pagina salvata
            else -> pagina
        }

        try {
            navController.navigate(destinazione)
        } catch (e: Exception) {
            // Se la navigazione fallisce (es. route non valida), vai alla Homepage
            println("Errore navigazione a $destinazione: ${e.message}")
            navController.navigate("Homepage")
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CircularProgressIndicator()
    }
}