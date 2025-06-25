package com.example.prova3

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GestioneMenu : ViewModel() {

    private val posizione = Location(9.19f, 45.4642f)
    private var base64 : String? by mutableStateOf(null)

    /** ❶ stato osservabile dalla UI  */
    var menuList by mutableStateOf<List<Menu>>(emptyList())
        private set

    /** ❷ eventuale messaggio d’errore (facoltativo) */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    /** ❸ avvia la rete dentro lo scope della ViewModel */
    fun caricaMenu() = viewModelScope.launch {
        try {
            menuList = CommunicationController.getMenus(
                lat = posizione.lat,
                lng = posizione.lng
            )
            errorMessage = null                        // reset eventuali errori precedenti
            Log.d("GestioneMenu", "Dati ottenuti")
        } catch (e: Exception) {
            Log.d("GestioneMenu", "Errore: ${e.message}")
            errorMessage = e.message                  // la UI può mostrarlo se vuole
        }
    }

    /*fun caricaImmagine() = viewModelScope.launch {
        try {
            base64 = CommunicationController.getImage(56)
        }catch (e : Exception){
            Log.d("GestioneMenu","Error ${e.message}")
        }
    }*/
}
