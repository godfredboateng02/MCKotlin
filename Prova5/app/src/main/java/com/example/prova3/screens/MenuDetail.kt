package com.example.prova3.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.prova3.R
import com.example.prova3.repository.GestioneAccountRepository
import com.example.prova3.repository.GestioneMenuRepository
import com.example.prova3.repository.GestioneOrdiniRepository
import com.example.prova3.viewmodel.MenuDetailViewModel

@Composable
fun MenuDetail(navController: NavController, gestioneMenuRepository: GestioneMenuRepository, gestioneAccountRepository: GestioneAccountRepository, gestioneOrdiniRepository: GestioneOrdiniRepository, mid : Int){

    val factory = viewModelFactory {
        initializer {
            MenuDetailViewModel(gestioneMenuRepository,gestioneAccountRepository,gestioneOrdiniRepository)
        }
    }
    val viewModel: MenuDetailViewModel = viewModel(factory = factory)
    val menuDettaglio = viewModel.menuDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMenuDetail(mid)
    }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //immagine
        Image(
            painter = painterResource(id = R.drawable.cibo),
            contentDescription = "Immagine di un menu",
            contentScale = ContentScale.Crop,
            modifier = image,
        )

        Log.d("MenuDetail",menuDettaglio.value.toString())

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ){
            Column (
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(menuDettaglio.value?.nome.toString(), style = titoloStyle, modifier = titoloModifier)


                //Descrizione breve
                Text("Descrizione Completa", style = descrizioneStyle)

                //Descrizionelunga
                Text(menuDettaglio.value?.descrizione.toString(), style = descrizioneLungaStyle)

                //Prezzo
                Text("10,99€", style = priceStyle, modifier = priceModifier)


                Spacer(modifier = Modifier.weight(1f))
                Text("Tempo di consegna stimato: 10 min", style = timeDistanceStyle)

                Button(
                    onClick = {Log.d("MenuDetails","Acquista button pressed")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)),
                    modifier = bottone

                ) {
                    Text("Effettua ordine 10,99€")
                }
            }
        }
    }
}

//Modifier
private var image = Modifier
    .fillMaxWidth()
    .padding(bottom = 50.dp)

private var bottone = Modifier
    .fillMaxWidth(0.8f)
    .height(50.dp)

private var titoloModifier = Modifier
    .padding(bottom = 20.dp)

private var priceModifier = Modifier
    .padding(top = 20.dp)
//Style
private var titoloStyle = TextStyle(
    fontSize = 30.sp,
    fontWeight = FontWeight.Bold,
    color = Color(0xFFFF8C00)
)

var descrizioneStyle = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xFFFF8C00)
)

var descrizioneLungaStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Light,
    color = Color(0xFF777777),
    textAlign = TextAlign.Center
)

var priceStyle = TextStyle(
    fontSize = 50.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xFF22BD3F)
)

var timeDistanceStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Light,
    color = Color(0xFF777777)
)

private val headerStyle = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xffffffff)
)