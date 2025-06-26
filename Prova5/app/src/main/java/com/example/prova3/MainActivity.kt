package com.example.prova3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prova3.model.CommunicationController
import com.example.prova3.model.GetUserInfo
import com.example.prova3.model.LocationManager
import com.example.prova3.model.Menu
import com.example.prova3.model.MenuBuyed
import com.example.prova3.model.MenuDetails
import com.example.prova3.model.OrderStatus
import com.example.prova3.model.PutUserInfo
import com.example.prova3.model.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // In MyApplication.onCreate():
        Storage.initialize(this)
        LocationManager.initialize(this)
        CommunicationController.initialize(this)

// Fatto! Ora funziona ovunque nell'app
        enableEdgeToEdge()
        setContent {
            MenuScreen()
        }
    }
}

val userUpdate = PutUserInfo(
    firstName = "Luca",
    lastName = "Rossi",
    cardFullName = "Luca Rossi",
    cardNumber = "1234123412341234",
    cardExpireMonth = 12,
    cardExpireYear = 2026,
    cardCVV = "123",
    sid = Storage.getSid()
)

@Preview
@Composable
fun App(){

    println(userUpdate)

    var oid: Int? by remember { mutableStateOf(null) }
    var textToShow by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = textToShow)

        //Bottone: getUserInfo
        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {

                try {
                    val response: GetUserInfo = CommunicationController.getUserInfo()
                    textToShow = "Risposta ricevuta"
                    Log.d("MainActivity", "Response: $response")
                }catch (e : Exception) {
                    Log.d("MainActivity", "Error: ${e.message}")
                    textToShow = "Errore: ${e.message}"
                }
            }
        }) {
            Text(text = "getUserInfo")
        }

        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    CommunicationController.putUserInfo(userUpdate)
                    Log.d("MainActivity","ok")
                }catch (e: Exception){
                    Log.d("MainActivity", "Error: ${e.message}")
                }
            }
        }) {
            Text(text = "putUserInfo")
        }

        Button(onClick = {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val risposta : List<Menu> = CommunicationController.getMenus()
                    if (risposta != null){
                        Log.d("MainActivity", risposta.toString())
                    }
                }

            }catch(e : Exception){
                Log.d("MainActivity", "Error: ${e.message}")
            }
        }) {
            Text("getMenus")
        }

        Button(onClick = {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val risposta : MenuDetails = CommunicationController.getMenuDetails(mid=52)
                    if (risposta != null){
                        Log.d("MainActivity", risposta.toString())
                    }
                }
            }catch (e: Exception){
                Log.d("MainActivity", "Error: ${e.message}")
            }
        }) {
            Text("getMenuDetails")
        }

        Button(onClick = {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val menuBuyed : MenuBuyed? = CommunicationController.postOrder(mid= 56)
                    if (menuBuyed != null){
                        Log.d("MainActivity",menuBuyed.toString())
                        oid = menuBuyed.oid
                    }
                }
            }catch (e: Exception){
                Log.d("MainActivity","Error ${e.message}")
            }
        }) {
            Text("buyMenu")
        }

        Button(onClick = {
            try{
                CoroutineScope(Dispatchers.Main).launch {
                    val risultato : OrderStatus? = CommunicationController.getOrderStatus(oid!!)
                    if (risultato != null){
                        Log.d("MainActivity",risultato.toString())
                    }
                }
            }catch (e: Exception){
                Log.d("MainActivity","Error ${e.message}")
            }
        }) {
            Text("getOrderStatus")
        }

        Button(onClick = {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    CommunicationController.deleteLastOrder()
                }
            }catch(e: Exception){
                Log.d("MainActivity","${e.message}")
            }
        }) {
            Text("Delete Order")
        }
    }
}

@Composable
fun MenuScreen() {
    val viewModel: GestioneMenu = viewModel()

    // Chiede i dati una sola volta
    LaunchedEffect(Unit) { viewModel.caricaMenu() }

    val lista = viewModel.menuList
    val errore = viewModel.errorMessage

    when {
        errore != null -> Text("Errore: $errore")
        lista.isEmpty() -> Text("Sto caricando i menu…")
        else -> MenuListView(lista)
    }
}



