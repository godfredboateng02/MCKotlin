package com.example.prova4.components

import MenuImageView
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova3.model.MenuListItem

@Composable
fun MenuElement(menu: MenuListItem, navController: NavController,){
    Box(modifier = box
        .clickable {
            Log.d("MenuElement", "main")
            navController.navigate("MenuDetail/${menu.mid}")
        }
    ){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {




            MenuImageView(
                immagine       = menu.immagine,                    // id del menu
                modifier  = image.aspectRatio(1f)                     // quadrata; cambia se vuoi
            )

            Text(menu.nome, style = titoloText)
            Text(menu.descrizione, style = descizioneText, maxLines = 3, overflow = TextOverflow.Ellipsis)

            Row (modifier = priceMinutesRow, horizontalArrangement = Arrangement.SpaceBetween,){
                //Prezzo
                Text(menu.prezzo.toString()+"€",style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color(0xFF22BD3F)))
                //Minuti
                Text(menu.tempo.toString()+" min ⏱️", modifier = Modifier.padding(top = 5.dp),style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFFFF7300)))
            }
        }
    }
}
//MODIFIERS
val box = Modifier
    .shadow(                                 // ⬅️ ombreggiatura leggera
        elevation = 8.dp,                    // altezza (4-8 dp è “leggero”)
        shape = RoundedCornerShape(16.dp),   // stessa forma del clip
        ambientColor = Color.Black.copy(alpha = 0.20f), // facoltativi
        spotColor    = Color.Black.copy(alpha = 0.25f)  // per regolare il soft shadow
    )
    .width(187.dp).wrapContentHeight()
    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
    .background(Color(0xFFFFFFFF))


private val image = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)


val priceMinutesRow = Modifier
    .fillMaxWidth()
    .padding(top = 15.dp, start = 15.dp, end = 15.dp)

//TEXTSTYLE

val titoloText = TextStyle(
    fontSize = 20.sp,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    color = Color(0xFFFF6600),
)

val descizioneText = TextStyle(
    color = Color(0xFF666666),
    textAlign = TextAlign.Center,
    fontSize =  18.sp,
    fontWeight = FontWeight.Medium
)