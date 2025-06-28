package com.example.prova3.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.navigation.NavController

import com.example.prova3.model.Storage


@Composable
fun LoadingScreen(navController: NavController){




    LaunchedEffect(Unit) {
        //spinning wheel
        navController.navigate(Storage.getPagina())
    }

        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            CircularProgressIndicator()
        }
    }


