package com.example.prova3.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.prova3.R
import com.example.prova3.components.LastOrderView
import com.example.prova3.model.repository.GestioneOrdiniRepository
import com.example.prova3.viewmodel.DeliveryViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage


@Composable
fun Delivery(navController: NavController, gestioneOrdiniRepository: GestioneOrdiniRepository) {

    val factory = viewModelFactory {
        initializer {
            DeliveryViewModel(gestioneOrdiniRepository)
        }
    }
    val viewModel: DeliveryViewModel = viewModel(factory = factory)

    val ultimoOrdine = viewModel.lastOrderMenu.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val orderStatus = viewModel.orderStatus.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.lastOrderMenu()
        viewModel.getOrderStatus()
    }

    Log.d("Delivery",orderStatus.toString())

    if (isLoading.value != true){
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(13.0)
                        center(Point.fromLngLat(9.23, 45.476))
                        pitch(0.0)
                        bearing(10.0)
                    }
                },
            ) {
                val partenzaIcon = rememberIconImage(
                    key = R.drawable.location_marker,
                    painter = painterResource(R.drawable.location_marker),
                )
                /*PointAnnotation(point = Point.fromLngLat(9.2303, 45.4769)) {
                    iconImage = partenzaIcon
                    iconSize = 0.3 // Scala del marker: 1.0 = originale, 2.0 = doppio
                }*/

                val destinazioneIcon = rememberIconImage(
                    key = R.drawable.location_marker,
                    painter = painterResource(R.drawable.location_marker),
                )
                PointAnnotation(point = Point.fromLngLat(9.2333, 45.5769)) {
                    iconImage = destinazioneIcon
                    iconSize = 0.3 // Scala del marker: 1.0 = originale, 2.0 = doppio
                }

                val droneIcon = rememberIconImage(
                    key = R.drawable.drone,
                    painter = painterResource(R.drawable.drone),
                )
                PointAnnotation(point = Point.fromLngLat(9.2303, 45.4769)) {
                    iconImage = droneIcon
                    iconSize = 2.0 // Scala del marker: 1.0 = originale, 2.0 = doppio
                }


                val pList = mutableListOf<Point>()
                val pList2 = mutableListOf<Point>()
                val partenza = Point.fromLngLat(9.2333,45.5769)
                val destinazione = Point.fromLngLat(9.2303,45.4769)
                val drone = Point.fromLngLat(9.2303, 45.4769)
                pList.add(partenza)
                pList.add(drone)
                pList2.add(drone)
                pList2.add(destinazione)
                PolylineAnnotation(pList){
                    lineColor = Color(0xFF8200FD)
                    lineWidth = 6.0
                }
                PolylineAnnotation(pList2){
                    lineColor = Color(0xFF8200FD)
                    lineWidth = 6.0
                }


            }

            Column(
                modifier = Modifier.fillMaxWidth().height(300.dp).background(Color.White).align(Alignment.BottomCenter),
            ) {

                Spacer(Modifier.height(20.dp))
                LastOrderView(ultimoOrdine.value)

                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {

                        if(orderStatus.value?.tempoRimanente == null){
                            Text(
                                text = "In consegna tra:" ,
                                modifier = Modifier.padding(bottom = 10.dp),
                                style = TextStyle(fontSize = 20.sp)
                            )

                            Text(
                                text = orderStatus.value?.tempoRimanente.toString(),
                                modifier = Modifier.padding(bottom = 10.dp),
                                style = TextStyle(fontSize = 20.sp, color = Color(0XFFFF7300))

                            )
                        }else{
                            Text(
                                text = "Consegnato" ,
                                modifier = Modifier.padding(bottom = 10.dp),
                                style = TextStyle(fontSize = 20.sp)
                            )
                        }


                    }

                    Button(
                        onClick = {
                            navController.navigate("Homepage")
                            viewModel.confermaRicezione()
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xffff7300))
                    ) {
                        Text(
                            "Conferma ricezione",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }

        }
    }else{
        CircularProgressIndicator()
    }
}