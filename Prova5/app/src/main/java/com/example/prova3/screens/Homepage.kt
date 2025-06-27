package com.example.prova3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova3.MenuListView
import com.example.prova3.R
import com.example.prova3.viewmodel.HomepageViewModel

@Composable
fun Homepage(navController: NavController,viewModel: HomepageViewModel){

    val listaMenu = viewModel.listaMenu.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getListaMenu()
    }
    Column(
        Modifier.fillMaxSize(),
    ){
        Box(
            Modifier.fillMaxWidth().height(120.dp),
        ){
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("I nostri menu", Modifier.padding(top = 50.dp, start = 16.dp),style=TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF7300)))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.padding(top=45.dp, end = 20.dp).size(70.dp).clip(CircleShape).padding().clickable{
                        navController.navigate("Profile")
                    },
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Box(){
            listaMenu.value?.let {
                MenuListView(it)
            }
        }
        //MenuListView()
    }
}