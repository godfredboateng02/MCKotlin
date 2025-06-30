package com.example.prova3.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.prova3.R
import com.example.prova3.components.MenuListView
import com.example.prova3.model.repository.GestioneMenuRepository
import com.example.prova3.viewmodel.HomepageViewModel

@Composable
fun Homepage(navController: NavController, gestioneMenuRepository: GestioneMenuRepository) {
    BackHandler { /* gestione back se necessaria */ }

    val factory = viewModelFactory {
        initializer { HomepageViewModel(gestioneMenuRepository) }
    }
    val viewModel: HomepageViewModel = viewModel(factory = factory)
    val listaMenu = viewModel.listaMenu.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val consegnaInCorso = viewModel.consegnaInCorso.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getListaMenu()
        viewModel.checkConsegnaInCorso()
    }

    if (isLoading.value == false) {

        /* ------------ CONTENITORE PRINCIPALE + OVERLAY ------------ */
        Box(Modifier.fillMaxSize()) {

            /* ----------------- CONTENUTO ESISTENTE ----------------- */
            Column(Modifier.fillMaxSize()) {

                // Header
                Box(Modifier.fillMaxWidth().height(120.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "I nostri menu",
                            Modifier.padding(top = 50.dp, start = 16.dp),
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF7300)
                            )
                        )
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .padding(top = 45.dp, end = 20.dp)
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable { navController.navigate("Profile") },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Lista menu
                Box {
                    listaMenu.value?.let { MenuListView(it, navController) }
                }
            }

            /* ---------------- LOGO FLOTANTE ---------------- */


            if (consegnaInCorso.value){
                Image(
                    painter = painterResource(R.drawable.delivery_icon),
                    contentDescription = "Delivery",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomEnd)          // in basso a destra
                        .padding(end = 24.dp, bottom = 24.dp)
                        .clickable { navController.navigate("Delivery") }
                )
            }
        }

    } else {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}