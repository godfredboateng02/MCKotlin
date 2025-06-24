package com.example.prova4

import MenuDetails
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova4.screens.EditProfileCard
import com.example.prova4.screens.EditProfileData
import com.example.prova4.screens.FirstScreen
import com.example.prova4.screens.Homepage
import com.example.prova4.screens.Profile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController, "FirstScreen"){
                composable (route ="FirstScreen"){
                    FirstScreen(navController)
                }

                composable (route = "Homepage"){
                    Homepage(navController)
                }

                composable(route = "MenuDetails") {
                    MenuDetails()
                }

                composable(route = "Profile"){
                    Profile(navController)
                }

                composable (route = "EditProfileData"){
                    EditProfileData()
                }

                composable (route = "EditProfileCard"){
                    EditProfileCard()
                }
            }
        }
    }
}
