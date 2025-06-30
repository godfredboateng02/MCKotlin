package com.example.prova3.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prova3.R
import com.example.prova3.model.LastOrderMenu

val descrizioneCorta = "Assortimento elegante di nigiri lucidi, maki arrotolati con precisione, sashimi di tonno rosso vivo e salmone vellutato, decorato con wasabi pungente"

@Composable
fun LastOrderView(ultimoOrdine: LastOrderMenu?){

    Log.d("LastOrderView","ultimoOrdine: ${ultimoOrdine.toString()}")

    if (ultimoOrdine != null){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0XFFFFFFFF))

        ){
            Row (
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    painter = painterResource(id = R.drawable.cibo),
                    contentDescription = ("menu immagine"),
                    modifier = Modifier
                        .padding(7.dp)
                        .fillMaxHeight()
                        //.size(130.dp,130.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .aspectRatio(1f)
                    //.size(150.dp),

                )


                Column(
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 15.dp, end = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(ultimoOrdine.nome ?: "", style = titleTextStyle)
                    Text(ultimoOrdine.descrizione, style = descrizioneCortaStyle, maxLines = 3)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("${ultimoOrdine.prezzo}€", style = priceStyle)
                }
            }
        }
    }else{
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Nessun ordine recente")
        }
    }




}

private val titleTextStyle = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium
)

private val descrizioneCortaStyle = TextStyle(
    fontSize = 17.sp,

    )

private val priceStyle = TextStyle(
    fontSize = 25.sp,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF009436)
)