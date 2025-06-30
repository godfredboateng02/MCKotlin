package com.example.prova3.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.prova3.viewmodel.FirstScreenViewModel


@Composable
fun FirstScreen(
    navController: NavController,
    gestioneAccountRepository: GestioneAccountRepository
) {

    BackHandler { /* se ti serve intercettare il back */ }

    // ───── INPUT STATE ─────
    var cognome by rememberSaveable { mutableStateOf("") }
    var nome    by rememberSaveable { mutableStateOf("") }

    // ───── VIEWMODEL ─────
    val factory = viewModelFactory {
        initializer { FirstScreenViewModel(gestioneAccountRepository) }
    }
    val viewModel: FirstScreenViewModel = viewModel(factory = factory)

    // ───── VALIDAZIONE ─────
    val formValid = cognome.isNotBlank() && nome.isNotBlank()
    val maxLen    = 15

    Column(modifier = Modifier.fillMaxSize()) {

        // ----- HEADER -----
        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .height(100.dp)
                .background(Color(0xFFFF7300))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Inserisci dati del profilo",
                    modifier = Modifier.padding(top = 66.dp),
                    style = titoloStyle
                )
            }
        }

        // ----- COGNOME -----
        Text(
            "Cognome",
            modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )
        OutlinedTextField(
            value = cognome,
            onValueChange = { new ->
                if (new.length <= maxLen) cognome = new        // blocca oltre 15
            },
            placeholder = { Text("es. Rossi") },
            singleLine  = true,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        Spacer(Modifier.height(30.dp))

        // ----- NOME -----
        Text(
            "Nome",
            modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )
        OutlinedTextField(
            value = nome,
            onValueChange = { new ->
                if (new.length <= maxLen) nome = new           // blocca oltre 15
            },
            placeholder = { Text("es. Mario") },
            singleLine  = true,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        Spacer(Modifier.height(50.dp))

        // ----- PULSANTE CONFERMA -----
        Button(
            onClick = {
                Log.d("EditProfileData", "Modifica dei dati… $cognome $nome")
                viewModel.updateUserData(nome, cognome)
                navController.navigate("Homepage")
            },
            enabled = formValid,                                // disattiva finché non valido
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor          = Color(0xFF009436),
                disabledContainerColor  = Color(0xFF009436).copy(alpha = 0.4f) // opaco
            )
        ) {
            Text(
                "Conferma le modifiche",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

/* ───── STILI ───── */
private val titoloStyle = TextStyle(
    fontSize   = 20.sp,
    fontWeight = FontWeight.Medium,
    color      = Color.White
)

private val CognomeStyle = TextStyle(
    fontSize   = 20.sp,
    fontWeight = FontWeight.Medium
)