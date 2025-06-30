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

    // Equivalente di extractTime() dal JavaScript
    /*fun extractTime(stringa: String?): TimeData? {
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
    }*/

    /**
     * Converte una stringa ISO-8601 in un oggetto [TimeData].
     *
     * @param isoUtcZ   stringa tipo "2025-06-30T16:21:15.072Z"
     * @return          TimeData(data = "2025-06-30", ora = "16:21:15")
     */
    fun extractTime(isoUtcZ: String): TimeData {

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

    // Equivalente di tempoRimanente() dal JavaScript
    /*fun tempoRimanente(expectedTime: String?): Int? {

        Log.d("Formattazione","Ehi sono qui")

        Log.d("Formattazione",expectedTime!!)
        if (expectedTime == null) {
            return null
        }
        val expectedDateTime = LocalDateTime.parse(expectedTime.replace(" ", "T"))
        val now = LocalDateTime.now()

        val differenceMinutes = java.time.Duration.between(now, expectedDateTime).toMinutes()
        val tempo = if (differenceMinutes > 0) differenceMinutes.toInt() else 0

        println("tempo rimanente: $tempo")
        Log.d("Formattazione",tempo.toString())
        return tempo
    }*/

    /**
     * Restituisce i minuti mancanti fra l’istante corrente (Instant.now())
     * e la stringa ISO-8601 passata (es. "2025-06-30T16:21:15.072Z").
     *
     * @param utcIsoZ timestamp in formato ISO-8601 con suffisso ‘Z’ (UTC).
     * @return minuti rimanenti (valore ≥ 0). Se la data è già passata, torna 0.
     */
    fun tempoRimanente(utcIsoZ: String): Int {
        val target = Instant.parse(utcIsoZ)
        val now    = Instant.now()

        val diff   = Duration.between(now, target).toMinutes()

        // Se diff è negativo vuol dire che il momento è già trascorso
        return diff.coerceAtLeast(0).toInt()
    }
}