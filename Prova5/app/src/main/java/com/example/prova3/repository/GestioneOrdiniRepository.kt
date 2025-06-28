// GestioneOrdiniRepository.kt
package com.example.prova3.repository

import androidx.collection.floatObjectMapOf
import com.example.prova3.model.CommunicationController
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.Location
import com.example.prova3.model.Storage
import com.example.prova3.model.TimeData

class GestioneOrdiniRepository {

    data class OrderStatusCompact(
        val stato: String,
        val partenza: Location?,
        val destinazione: Location,
        val drone: Location,
        val tempoRimanente: Int?, // FormattazioneRepository.TimeData o Int (per tempo rimanente)
        val orarioConsegna : TimeData?
    )


    suspend fun consegnaInCorso(): Boolean {
        return Storage.inConsegna()
    }

    suspend fun effettuaOrdine(mid: Int) {
            try {
            Storage.setConsegna(true)
            println("Acquisto effettuato")

            val ordine = CommunicationController.postOrder(mid)
            if (ordine == null) {
                Storage.setConsegna(false)
            }

            Storage.setRistorante(mid)
            Storage.setOid(ordine?.oid ?: -1)
            Storage.setMid(mid)

            orderStatus()
        } catch (error: Exception) {
            println("errore in effettuaOrdine: $error")
            Storage.setConsegna(false)
        }
    }

    suspend fun lastOrderMenu(): LastOrderMenu? {
        val mid = Storage.getMid() ?: return null

        println("mid: $mid")
        val menu = CommunicationController.getMenuDetails(mid)

        return LastOrderMenu(
            nome = menu.name,
            prezzo = String.format("%.2f", menu.price),
            descrizione = menu.shortDescription,
            immagine = Storage.getImage(mid, menu.imageVersion)
        )
    }

    // Equivalente di orderStatus() dal JavaScript
    suspend fun orderStatus(): OrderStatusCompact? {
        try {
            val oid = Storage.getOid()
            println("PRE-->: $oid")
            if (oid == null) {
                return null
            }

            val raw = CommunicationController.getOrderStatus(oid)
            println("POST orderstatus: $raw")
            if (raw == null) {
                return null
            }

            val formattazione = FormattazioneRepository()
            val tempoRimanente = formattazione.tempoRimanente(raw.expectedDeliveryTimestamp)
            val orarioConsegna = formattazione.extractTime(raw.deliveryTimestamp)



            return OrderStatusCompact(
                stato = raw.status,
                partenza = Storage.getRistorante(),
                destinazione = raw.deliveryLocation,
                drone = raw.currentPosition,
                tempoRimanente = tempoRimanente,
                orarioConsegna = orarioConsegna
            )
        } catch (error: Exception) {
            println("errore in orderStatus: $error")
            return null
        }
    }

    suspend fun confermaConsegna() {
        Storage.setConsegna(false)
    }
}