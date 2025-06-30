package com.example.prova3.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PageOfShame(navController: NavController){
    BackHandler {

    }



    // CARICA LA LISTA DEI MENU CON LE LORO IMMAGINI



        Column(
            Modifier.fillMaxSize(),
        ){
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),
            ){
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text("PAGE OF SHAME", Modifier.padding(top = 50.dp, start = 16.dp),style=TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF7300)))
                    Text(text = "Per usare l'app servono i permessi per la posizione precisa", modifier = Modifier.padding(top = 50.dp, start =16.dp, end = 16.dp, bottom = 20.dp), style =TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000), textAlign = TextAlign.Center) )
                    Button(onClick = { navController.navigate("LoadingScreen") }) {
                        Text(text = "riprova")
                    }

                }
            }

        }



}