package com.example.prova3.repository

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.prova3.model.TimeData
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

object Formattazione {



    /**
     * Converte una stringa ISO-8601 in un oggetto [TimeData].
     *
     * @param isoUtcZ   stringa tipo "2025-06-30T16:21:15.072Z"
     * @return          TimeData(data = "2025-06-30", ora = "16:21:15")
     */
    fun extractTime(isoUtcZ: String?): TimeData? {

        if (isoUtcZ == null){
            return null
        }
        Log.d("Formattazione","Ok sono qui")
        val ldt = Instant             // parse in UTC
            .parse(isoUtcZ)
            .atZone(ZoneOffset.UTC)
            .toLocalDateTime()

        Log.d("Formattazione","Ok sono qui ${ldt}")

        return TimeData(
            data = ldt.toLocalDate().toString(),               // "2025-06-30"
            ora  = ldt.toLocalTime().withNano(0).toString()    // "16:21:15"
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


    fun tempoRimanente(utcIsoZ: String): Int {
        val target = Instant.parse(utcIsoZ)
        val now    = Instant.now()

        val diff   = Duration.between(now, target).toMinutes()

        // Se diff è negativo vuol dire che il momento è già trascorso
        return diff.coerceAtLeast(0).toInt()
    }
}