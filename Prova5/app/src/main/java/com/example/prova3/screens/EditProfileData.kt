package com.example.prova3.screens

import android.util.Log
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
import com.example.prova3.repository.GestioneAccountRepository
import com.example.prova3.repository.GestioneAccountRepository.UpdateNameData
import com.example.prova3.viewmodel.EditProfileViewModel
import com.example.prova3.viewmodel.ProfileViewModel

@Composable
fun EditProfileData(navController: NavController, gestioneAccountRepository: GestioneAccountRepository){
    var cognome by rememberSaveable { mutableStateOf("") }
    var nome by rememberSaveable { mutableStateOf("") }

    val factory = viewModelFactory {
        initializer {
            EditProfileViewModel(gestioneAccountRepository)
        }
    }
    val viewModel: EditProfileViewModel = viewModel(factory = factory)


    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.padding(bottom = 30.dp)
                .height(100.dp)
                .background(Color(0XFFFF7300))
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
            ){
                Text("<", modifier = Modifier.weight(1f).padding(top = 66.dp, start = 16.dp))
                Text("Profilo / Modifica dati del profilo", modifier = Modifier.weight(5f).padding(top = 66.dp), style = titoloStyle)
            }
        }

        Text("Cognome", modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = cognome,
            onValueChange = {
                cognome = it
            },
            placeholder = {Text("es.Rossi")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp)

        )

        Spacer(Modifier.height(30.dp))

        Text("Nome", modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            placeholder = {Text("es. Mario")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp)

        )

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = {
                viewModel.updateUserData(nome,cognome)
                navController.popBackStack(route = "Profile", inclusive = false)
            },
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp).height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff009436))
        ) {
            Text("Conferma le modifiche", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))
        }



    }
}

private val titoloStyle = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xffffffff)
)

private val CognomeStyle = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
)