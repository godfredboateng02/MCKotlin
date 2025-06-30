import android.R.attr.text
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
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun EditProfileCard(
    navController: NavController,
    gestioneAccountRepository: GestioneAccountRepository
) {
    BackHandler { navController.navigate("Profile") }

    /* ---------- STATE ---------- */
    var fullName          by rememberSaveable { mutableStateOf("") }
    var numberState       by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var expireMonthString by rememberSaveable { mutableStateOf("") }
    var expireYearString  by rememberSaveable { mutableStateOf("") }
    var cvv               by rememberSaveable { mutableStateOf("") }

    /* ---------- ERROR FLAGS ---------- */
    var fullNameError  by remember { mutableStateOf(false) }
    var numberError    by remember { mutableStateOf(false) }
    var expireMonthErr by remember { mutableStateOf(false) }
    var expireYearErr  by remember { mutableStateOf(false) }
    var cvvError       by remember { mutableStateOf(false) }

    /* ---------- VIEWMODEL ---------- */
    val factory = viewModelFactory { initializer { EditCardViewModel(gestioneAccountRepository) } }
    val viewModel: EditCardViewModel = viewModel(factory = factory)
    val done = viewModel.done.collectAsState()
    if (done.value) navController.navigate("Profile")

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    /* ---------- UI ---------- */
    Column(Modifier.fillMaxSize()) {

        /* ---------- HEADER ---------- */
        Box(
            Modifier
                .padding(bottom = 30.dp)
                .height(100.dp)
                .background(Color(0xFFFF7300))
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(" ", Modifier.weight(1f).padding(top = 66.dp, start = 16.dp))
                Text(
                    "Profilo / Modifica dati della carta",
                    Modifier.weight(5f).padding(top = 66.dp),
                    style = titoloStyle
                )
            }
        }

        /* ---------- FULL NAME ---------- */
        Text("Card FullName", Modifier.padding(start = 50.dp, bottom = 10.dp), style = CognomeStyle)

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                if (it.length <= 31) {
                    fullName = it
                    fullNameError = it.isBlank()
                }
            },
            placeholder = { Text("Mario Rossi") },
            singleLine  = true,
            isError     = fullNameError,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
        if (fullNameError) {
            errorText(
                "Il nome è obbligatorio",
                Modifier.padding(start = 50.dp)   // ← rientro come il campo
            )
        }

        Spacer(Modifier.height(30.dp))

        /* ---------- CARD NUMBER ---------- */
        Text("Card Number", Modifier.padding(start = 50.dp, bottom = 10.dp), style = CognomeStyle)

        OutlinedTextField(
            value = numberState,
            onValueChange = { newState ->
                val digits = newState.text.filter(Char::isDigit).take(16)
                val formatted = digits.chunked(4).joinToString(" ")
                numberState = newState.copy(
                    text = formatted,
                    selection = TextRange(formatted.length)   // cursore in fondo
                )
                numberError = digits.length != 16
            },
            placeholder = { Text("1234 5678 1234 5678") },
            singleLine  = true,
            isError     = numberError,
            modifier    = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
        if (numberError) {
            errorText(
                "La carta deve avere 16 cifre",
                Modifier.padding(start = 50.dp)   // ← rientro come il campo
            )
        }

        Spacer(Modifier.height(30.dp))

        /* ---------- EXP DATE & CVV ---------- */
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Expire date", Modifier.padding(bottom = 10.dp), style = CognomeStyle)
                Row {
                    OutlinedTextField(
                        value = expireMonthString,
                        onValueChange = {
                            if (it.length <= 2 && it.all(Char::isDigit)) {
                                expireMonthString = it
                                val m = it.toIntOrNull() ?: -1
                                expireMonthErr = m !in 1..12
                            }
                        },
                        placeholder = { Text("mm") },
                        singleLine  = true,
                        isError     = expireMonthErr,
                        modifier    = Modifier.width(80.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    OutlinedTextField(
                        value = expireYearString,
                        onValueChange = {
                            if (it.length <= 4 && it.all(Char::isDigit)) {
                                expireYearString = it
                                val y = it.toIntOrNull() ?: -1
                                expireYearErr = y < currentYear || it.length != 4
                            }
                        },
                        placeholder = { Text("YYYY") },
                        singleLine  = true,
                        isError     = expireYearErr,
                        modifier    = Modifier.width(100.dp)
                    )
                }
                if (expireMonthErr) errorText("Mese 1-12")
                if (expireYearErr)  errorText("Anno non valido")
            }

            Column {
                Text("CVV/CVV2", Modifier.padding(bottom = 10.dp), style = CognomeStyle)
                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all(Char::isDigit)) {
                            cvv = it
                            cvvError = it.length != 3
                        }
                    },
                    placeholder = { Text("123") },
                    singleLine  = true,
                    isError     = cvvError,
                    modifier    = Modifier.width(80.dp)
                )
                if (cvvError) errorText("3 cifre")
            }
        }

        Spacer(Modifier.height(50.dp))

        /* ---------- BUTTON ---------- */
        val allOk =
            !(fullNameError || numberError || expireMonthErr || expireYearErr || cvvError) &&
                    fullName.isNotBlank() &&
                    numberState.text.filter(Char::isDigit).length == 16 &&
                    expireMonthString.isNotBlank() &&
                    expireYearString.isNotBlank() &&
                    cvv.length == 3

        Button(
            onClick = {
                val expireMonth = expireMonthString.toIntOrNull() ?: 0
                val expireYear  = expireYearString.toIntOrNull() ?: 0
                val cleanNumber = numberState.text.replace(" ", "")
                viewModel.updateCardData(fullName, cleanNumber, expireMonth, expireYear, cvv)
                Log.d("EditProfileCard", "$expireMonth $expireYear $cvv")
            },
            enabled = allOk,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor         = Color(0xFF009436),
                disabledContainerColor = Color(0xFF009436).copy(alpha = 0.4f)
            )
        ) {
            Text("Conferma le modifiche", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))
        }
    }
}

/* ---------- HELPERS ---------- */
@Composable
private fun errorText(msg: String, modifier: Modifier = Modifier) =
    Text(msg, color = Color.Red, fontSize = 12.sp, modifier = modifier.padding(top = 4.dp))

/* ---------- STILI ---------- */
private val titoloStyle  = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
private val CognomeStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)

