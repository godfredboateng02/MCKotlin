import androidx.compose.runtime.saveable.rememberSaveable

/*package com.example.prova3.screens

import android.util.Log
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
import androidx.compose.foundation.layout.width
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
import com.example.prova3.viewmodel.EditCardViewModel


@Composable
fun EditProfileCard(navController: NavController, gestioneAccountRepository: GestioneAccountRepository){
    var fullName by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var expireMonth by rememberSaveable { mutableStateOf<Int>(0) }
    var expireMonthString by rememberSaveable { mutableStateOf("") }
    var expireYear by rememberSaveable { mutableStateOf<Int>(0) }
    var expireYearString by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf<String>("") }

    val factory = viewModelFactory {
        initializer {
            EditCardViewModel(gestioneAccountRepository)
        }
    }
    val viewModel: EditCardViewModel = viewModel(factory = factory)

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
                Text("Profilo / Modifica dati della carta", modifier = Modifier.weight(5f).padding(top = 66.dp), style = titoloStyle)
            }
        }

        Text("Card FullName", modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
            },
            placeholder = {Text("Mario Rossi")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp)

        )

        Spacer(Modifier.height(30.dp))

        Text("Card Number", modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = number,
            onValueChange = {
                number = it
            },
            placeholder = {Text("1234 5678 1234 5678")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp)

        )

        Spacer(Modifier.height(30.dp))



        Row (modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                Text("Expire date", modifier = Modifier.padding(bottom = 10.dp),
                    style = CognomeStyle
                )

                Row {
                    OutlinedTextField(
                        value = expireMonthString,
                        onValueChange = {
                            expireMonthString = it
                        },
                        placeholder = {Text("mm")},
                        singleLine = true,
                        modifier = Modifier.width(80.dp)

                    )

                    Spacer(Modifier.width(10.dp))

                    OutlinedTextField(
                        value = expireYearString,
                        onValueChange = {
                            expireYearString = it
                        },
                        placeholder = {Text("YYYY")},
                        singleLine = true,
                        modifier = Modifier.width(80.dp)

                    )
                }

            }
            Column {
                Text("CVV/CVV2", modifier = Modifier.padding(bottom = 10.dp),
                    style = CognomeStyle
                )

                Row {
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            cvv = it
                        },
                        placeholder = {Text("123")},
                        singleLine = true,
                        modifier = Modifier.width(80.dp)

                    )
                }

            }



        }

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = {
                expireMonth = expireMonthString.toInt()
                expireYear = expireYearString.toInt()
                viewModel.updateCardData(fullName, number, expireMonth, expireYear, cvv)
                Log.d("EditProfileCard","${expireMonth} ${expireYear} ${cvv}")
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
)*/

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.prova3.viewmodel.EditCardViewModel
import java.util.*

@Composable
fun EditProfileCard(
    navController: NavController,
    gestioneAccountRepository: GestioneAccountRepository
) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var expireMonthString by rememberSaveable { mutableStateOf("") }
    var expireYearString by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf("") }

    var numberError by remember { mutableStateOf(false) }
    var fullNameError by remember { mutableStateOf(false) }
    var expireMonthError by remember { mutableStateOf(false) }
    var expireYearError by remember { mutableStateOf(false) }
    var cvvError by remember { mutableStateOf(false) }

    val factory = viewModelFactory {
        initializer {
            EditCardViewModel(gestioneAccountRepository)
        }
    }
    val viewModel: EditCardViewModel = viewModel(factory = factory)

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .height(100.dp)
                .background(Color(0XFFFF7300))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "<",
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 66.dp, start = 16.dp)
                )
                Text(
                    "Profilo / Modifica dati della carta",
                    modifier = Modifier
                        .weight(5f)
                        .padding(top = 66.dp),
                    style = titoloStyle
                )
            }
        }

        // FULL NAME
        Text(
            "Card FullName",
            modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                if (it.length <= 31) {
                    fullName = it
                    fullNameError = it.isBlank() || it.length > 31
                }
            },
            placeholder = { Text("Mario Rossi") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            isError = fullNameError
        )
        if (fullNameError) {
            Text(
                text = "Il nome deve avere da 1 a 31 caratteri",
                color = Color.Red,
                modifier = Modifier.padding(start = 50.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(30.dp))

        // CARD NUMBER
        Text(
            "Card Number",
            modifier = Modifier.padding(start = 50.dp, bottom = 10.dp),
            style = CognomeStyle
        )

        OutlinedTextField(
            value = number,
            onValueChange = {
                val digits = it.filter { c -> c.isDigit() }
                val formatted = digits.chunked(4).joinToString(" ")
                if (formatted.length <= 19) {
                    number = formatted
                    numberError = digits.length != 16
                }
            },
            placeholder = { Text("1234 5678 1234 5678") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            isError = numberError
        )
        if (numberError) {
            Text(
                text = "Il numero della carta deve avere 16 cifre",
                color = Color.Red,
                modifier = Modifier.padding(start = 50.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Expire date", modifier = Modifier.padding(bottom = 10.dp), style = CognomeStyle)

                Row {
                    OutlinedTextField(
                        value = expireMonthString,
                        onValueChange = {
                            if (it.length <= 2 && it.all { c -> c.isDigit() }) {
                                expireMonthString = it
                                val month = it.toIntOrNull() ?: -1
                                expireMonthError = month !in 1..12
                            }
                        },
                        placeholder = { Text("mm") },
                        singleLine = true,
                        modifier = Modifier.width(80.dp),
                        isError = expireMonthError
                    )

                    Spacer(Modifier.width(10.dp))

                    OutlinedTextField(
                        value = expireYearString,
                        onValueChange = {
                            if (it.length <= 4 && it.all { c -> c.isDigit() }) {
                                expireYearString = it
                                val year = it.toIntOrNull() ?: -1
                                expireYearError = year <= currentYear - 1 || it.length != 4
                            }
                        },
                        placeholder = { Text("YYYY") },
                        singleLine = true,
                        modifier = Modifier.width(100.dp),
                        isError = expireYearError
                    )
                }
                if (expireMonthError) {
                    Text(
                        text = "Mese 1-12, max 2 cifre",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                if (expireYearError) {
                    Text(
                        text = "Anno non valido",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Column {
                Text("CVV/CVV2", modifier = Modifier.padding(bottom = 10.dp), style = CognomeStyle)

                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all { c -> c.isDigit() }) {
                            cvv = it
                            cvvError = it.length != 3
                        }
                    },
                    placeholder = { Text("123") },
                    singleLine = true,
                    modifier = Modifier.width(80.dp),
                    isError = cvvError
                )
                if (cvvError) {
                    Text(
                        text = "Max 3 cifre",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = {
                val expireMonth = expireMonthString.toIntOrNull() ?: 0
                val expireYear = expireYearString.toIntOrNull() ?: 0
                val cleanNumber = number.replace(" ", "")
                viewModel.updateCardData(fullName, cleanNumber, expireMonth, expireYear, cvv)
                Log.d("EditProfileCard", "$expireMonth $expireYear $cvv")
                navController.popBackStack(route = "Profile", inclusive = false)
            },
            enabled = !(fullNameError || numberError || expireMonthError || expireYearError || cvvError),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff009436))
        ) {
            Text("Conferma le modifiche", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))
        }
    }
}

private val titoloStyle = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
    color = Color.White
)

private val CognomeStyle = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
)
