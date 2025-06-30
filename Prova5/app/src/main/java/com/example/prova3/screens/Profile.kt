package com.example.prova3.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.prova3.R
import com.example.prova3.components.CardElement
import com.example.prova3.components.LastOrderView
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneOrdiniRepository
import com.example.prova3.viewmodel.ProfileViewModel

@Composable
fun Profile(navController: NavController, gestioneAccountRepository: GestioneAccountRepository, gestioneOrdiniRepository: GestioneOrdiniRepository){

    BackHandler {
        navController.navigate("Homepage")
    }
    val factory = viewModelFactory {
        initializer {
            ProfileViewModel(gestioneAccountRepository,gestioneOrdiniRepository)
        }
    }
    val viewModel: ProfileViewModel = viewModel(factory = factory)
    val datiUtente = viewModel.datiUtente.collectAsState()
    val ultimoOrdine = viewModel.lastOrderMenu.collectAsState()

    val isLoadingUser = viewModel.isLoadingUser.collectAsState()
    val isLoadingMenu = viewModel.isLoadingMenu.collectAsState()

    val lastOrderTime = viewModel.lastOrderTime.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getUserData()
        viewModel.lastOrderMenu()
        viewModel.lastOrderTime()
    }

    if ( !isLoadingMenu.value && !isLoadingUser.value){
        datiUtente.value?.let {
            Log.d("Profile",it.toString())
        }


        Column(modifier = Modifier.fillMaxSize().background(Color(0xFFEEEEEE))) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(bottomEnd = 70.dp, bottomStart = 70.dp))
                    .background(Color(0xFFFF7300))
            ){
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        //painter = painterResource(id = R.drawable.profile_image),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        Modifier.size(100.dp)
                    )
                    Text("${datiUtente.value?.nome} ${datiUtente.value?.cognome}", style = nomeCognomeTextStyle, modifier = Modifier.padding(top = 40.dp, bottom = 10.dp))
                    Text("Modifica profilo", style = modificaProfiloTextStyle, modifier = Modifier
                        .clickable{navController.navigate("EditProfileData")
                        })
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp,end=16.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("La mia carta", style = titoliStyle)
                Text("modifica", style = TextStyle(Color(0xFF00A3FB), fontWeight = FontWeight.SemiBold), modifier = Modifier.padding(top = 10.dp).clickable{navController.navigate("EditProfileCard")})
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CardElement(navController, datiUtente.value)
            }





            //Ordini recenti
            Text("Ultimo ordine", modifier = Modifier.padding(start = 16.dp, top = 30.dp, bottom = 5.dp), style = titoliStyle)

            if (lastOrderTime.value != null){
                Text(text = "${lastOrderTime.value?.data}, ${lastOrderTime.value?.ora?.substring(0..4)}" , modifier = Modifier.padding(start = 16.dp, bottom = 10.dp))
            }

            if (ultimoOrdine.value != null){
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    LastOrderView(ultimoOrdine.value)
                }
            }else{
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text("nessun ordine recente")
                }
            }


            //La mia carta


        }
    }else{
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }


}


//STYLE
private val nomeCognomeTextStyle = TextStyle(
    fontSize = 30.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xFFFFFFFF)
)

private val modificaProfiloTextStyle = TextStyle(
    fontSize = 15.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xFF3D79BE)
)

private val titoliStyle = TextStyle(
    fontSize = 25.sp,
    fontWeight = FontWeight.Bold,
    color = Color(0xFFFF7300)
)