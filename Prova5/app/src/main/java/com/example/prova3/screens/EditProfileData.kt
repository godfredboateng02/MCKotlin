package com.example.prova3.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.viewmodel.EditProfileViewModel

@Composable
fun EditProfileData(
    navController: NavController,
    gestioneAccountRepository: GestioneAccountRepository
) {
    BackHandler { navController.navigate("profile") }

    /* ---------- STATE ---------- */
    var cognome by rememberSaveable { mutableStateOf("") }
    var nome    by rememberSaveable { mutableStateOf("") }

    /* ---------- VIEWMODEL ---------- */
    val factory = viewModelFactory { initializer { EditProfileViewModel(gestioneAccountRepository) } }
    val viewModel: EditProfileViewModel = viewModel(factory = factory)
    val done = viewModel.done.collectAsState()
    if (done.value) navController.navigate("Profile")

    /* ---------- VALIDAZIONE ---------- */
    val maxLen = 15
    val formOk = cognome.isNotBlank() && nome.isNotBlank()

    /* ---------- UI ---------- */
    Column(Modifier.fillMaxSize()) {

        /* Header identico */
        Box(
            Modifier
                .padding(bottom = 30.dp)
                .height(100.dp)
                .background(Color(0xFFFF7300))
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text("<", Modifier.weight(1f).padding(top = 66.dp, start = 16.dp))
                Text(
                    "Profilo / Modifica dati del profilo",
                    Modifier.weight(5f).padding(top = 66.dp),
                    style = titoloStyle
                )
            }
        }

        /* ---------- COGNOME ---------- */
        Text("Cognome", Modifier.padding(start = 50.dp, bottom = 10.dp), style = fieldLabelStyle)

        OutlinedTextField(
            value = cognome,
            onValueChange = { new ->
                if (new.length <= maxLen) cognome = new    // blocco oltre 15
            },
            placeholder = { Text("es. Rossi") },
            singleLine  = true,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        Spacer(Modifier.height(30.dp))

        /* ---------- NOME ---------- */
        Text("Nome", Modifier.padding(start = 50.dp, bottom = 10.dp), style = fieldLabelStyle)

        OutlinedTextField(
            value = nome,
            onValueChange = { new ->
                if (new.length <= maxLen) nome = new       // blocco oltre 15
            },
            placeholder = { Text("es. Mario") },
            singleLine  = true,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        Spacer(Modifier.height(50.dp))

        /* ---------- BUTTON ---------- */
        Button(
            onClick = {
                viewModel.updateUserData(nome, cognome)
                Log.d("EditProfileData", "Aggiornamento $cognome $nome")
            },
            enabled = formOk,                                // attivo solo se entrambi compilati
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor         = Color(0xFF009436),
                disabledContainerColor = Color(0xFF009436).copy(alpha = 0.4f) // opaco quando disabilitato
            )
        ) {
            Text(
                "Conferma le modifiche",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

/* ---------- STILI ---------- */
private val titoloStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
private val fieldLabelStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)