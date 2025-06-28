package com.example.prova3.repository

import com.example.prova3.model.TimeData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FormattazioneRepository {

    // Equivalente di extractTime() dal JavaScript
    fun extractTime(stringa: String?): TimeData? {
        if (stringa == null){
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

    // Equivalente di showImage() dal JavaScript
    fun showImage(image: String): String {
        return "data:image/png;base64," + image.drop(8).dropLast(1)
    }

    // Equivalente di tempoRimanente() dal JavaScript
    fun tempoRimanente(expectedTime: String?): Int? {
        if (expectedTime == null){
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
