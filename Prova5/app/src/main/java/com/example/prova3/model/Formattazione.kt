package com.example.prova3.repository

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.prova3.model.TimeData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.util.Base64

object Formattazione {

    // Equivalente di extractTime() dal JavaScript
    fun extractTime(stringa: String?): TimeData? {
        if (stringa == null) {
            return null
        }
        val dateTime = LocalDateTime.parse(stringa.replace(" ", "T"))

        val dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN)
        val oraFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ITALIAN)

        return TimeData(
            data = dateTime.format(dataFormatter),
            ora = dateTime.format(oraFormatter)
        )
    }

    // Equivalente di showImage() dal JavaScript - convertito per Compose
    fun formatImage(base64String: String): ImageBitmap? {
        return try {
            // Rimuove il prefixo se presente (es: "data:image/png;base64,")
            val cleanBase64 = if (base64String.startsWith("data:")) {
                base64String.substringAfter("base64,")
            } else {
                base64String
            }

            val bytes = Base64.decode(cleanBase64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            println("Errore decodifica immagine: ${e.message}")
            null
        }
    }

    // Equivalente di tempoRimanente() dal JavaScript
    fun tempoRimanente(expectedTime: String?): Int? {
        if (expectedTime == null) {
            return null
        }
        val expectedDateTime = LocalDateTime.parse(expectedTime.replace(" ", "T"))
        val now = LocalDateTime.now()

        val differenceMinutes = java.time.Duration.between(now, expectedDateTime).toMinutes()
        val tempo = if (differenceMinutes > 0) differenceMinutes.toInt() else 0

        println("tempo rimanente: $tempo")
        return tempo
    }
}