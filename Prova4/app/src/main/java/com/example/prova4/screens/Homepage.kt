package com.example.prova4.screens

import MenuListView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova4.R
import kotlinx.coroutines.delay

@Composable
fun Homepage(navController: NavController){
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(1000)
        isLoading = false
    }
    if (!isLoading) {
        Column(modifier = Modifier.padding(top = 40.dp)) {
            Row(modifier = header, verticalAlignment = Alignment.CenterVertically) {
                Text("I nostri menu", style = titolo, modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = logo.clickable {
                        navController.navigate("Profile")
                    },

                    )

            }
            MenuListView(navController)
        }
    }else{
        Column (
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
        }

    }
}

val header = Modifier
    .fillMaxWidth()
    .height(50.dp)
    .padding(horizontal = 16.dp)


val titolo = TextStyle(
    fontSize = 30.sp,
    fontWeight = FontWeight.Bold,
    color = Color(0xFFFF8C00)
)

val logo = Modifier
    .size(56.dp)
    //.clickable(onClick = {Log.d("Homepage","Vai alla pagina di profilo")})

