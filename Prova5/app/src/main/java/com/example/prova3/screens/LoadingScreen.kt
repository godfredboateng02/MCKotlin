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

    }




    LaunchedEffect(Unit) {
        //spinning wheel
        delay(70)
        val pagina = Storage.getPagina()
        if (LocationManager.hasLocationPermission() || pagina == "FirstScreen"){
            navController.navigate(pagina)
        }else{
            navController.navigate("PageOfShame")
        }
    }

        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            CircularProgressIndicator()
        }
    }


