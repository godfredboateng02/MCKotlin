/*package com.example.prova4.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova4.R


data class Menu (val nome: String, val descrizione: String, val prezzo: Float, val distanza: String)

@Composable
fun MenuElement(menu: Menu, navController: NavController){
    Box(modifier = box
        .clickable {
            Log.d("MenuElement", "main")
            navController.navigate("MenuDetails")
        }
    ){
       /* Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = r.drawable.cibo),
                contentDescription = "Immagine di un menu",
                contentScale = ContentScale.Crop,
                modifier = image,
            )

            Text(menu.nome, style = titoloText)
            Text(menu.descrizione, style = descizioneText)

            Row (modifier = priceMinutesRow, horizontalArrangement = Arrangement.SpaceBetween){
                //Prezzo
                Text(menu.prezzo.toString()+"€")
                //Minuti
                Text(menu.distanza)
            }
        }
    }
}*/
//MODIFIERS
val box = Modifier
    .size(187.dp,260.dp)
    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
    .background(Color(0xFFFFFFFF))


private val image = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)


val priceMinutesRow = Modifier
    .fillMaxWidth()

//TEXTSTYLE

val titoloText = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xFFFF6600)
)

val descizioneText = TextStyle(
    color = Color(0xFF666666),
    textAlign = TextAlign.Center
)
*/
