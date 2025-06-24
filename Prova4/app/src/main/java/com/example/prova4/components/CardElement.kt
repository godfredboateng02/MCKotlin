package com.example.prova4.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CardElement(navController: NavController){
    Box(
        modifier = card.clickable{
            navController.navigate("EditProfileCard")
        },

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            //visa
            Text("Visa",style = visaTextStyle, modifier = Modifier.padding(bottom = 30.dp))
            //riga 1
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text("Card number", style = cardTextStyle)
                    Text("**** **** **** 1234", style = cardNumberStyle)
                }

                Column {
                    Text("CVV", style = cardTextStyle)
                    Text("* * *", style = cardTextStyle)
                }

            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                //Riga 2
                Column {
                    Text("Expire Date",style = cardTextStyle)
                    Text("12/2027",style = cardExpireDate)
                }

                Column {
                    Text("Boateng Godfred",style = cardTextStyle)
                }
            }
        }
    }
}

private val card = Modifier
    .size(360.dp, 208.dp)
    .clip(RoundedCornerShape(16.dp))
    .background(Color(0xFF3A9DE9))
    .clickable(onClick = {Log.d("Profile","Carta di credito")})

val cardTextStyle = TextStyle(
    color = Color(0XFFFFFFFF)
)

val cardNumberStyle = TextStyle(
    color = Color(0xFFFFFFFF),
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium
)
val cardExpireDate = TextStyle(
    color = Color(0xFFFFFFFF),
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp
)

val visaTextStyle = TextStyle(
    color = Color(0XFFFFFFFF),
    fontSize = 25.sp,
    fontWeight = FontWeight.Bold
)