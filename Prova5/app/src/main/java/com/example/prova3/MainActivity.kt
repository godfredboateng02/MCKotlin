package com.example.prova3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova3.model.CommunicationController
import com.example.prova3.model.LocationManager
import com.example.prova3.model.Storage
import com.example.prova3.screens.Homepage
import com.example.prova3.screens.Profile
import com.example.prova3.viewmodel.HomepageViewModel
import com.example.prova3.viewmodel.ProfileViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Storage.initialize(this)
        LocationManager.initialize(this)
        CommunicationController.initialize(this)

        val homepageViewModel = ViewModelProvider(this)[HomepageViewModel::class]
        val profileViewModel = ViewModelProvider(this)[ProfileViewModel::class]

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController, "Homepage") {
                composable( route = "Homepage"){
                    Homepage(navController, homepageViewModel)
                }

                composable( route = "Profile"){
                    Profile(navController,profileViewModel )
                }
            }
        }
    }
}




