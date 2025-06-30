package com.example.prova3.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Allerta(
    messaggio: String,
    onChiudi: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onChiudi,
        title = {
            Text(
                text = "Errore",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF7300)
                )
            )
        },
        text = {
            Text(
                text = messaggio,
                style = TextStyle(fontSize = 16.sp)
            )
        },
        confirmButton = {
            TextButton(onClick = onChiudi) {
                Text(
                    "OK",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF7300)
                    )
                )
            }
        }
    )
}