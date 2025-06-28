// GestioneOrdiniRepository.kt
package com.example.prova3.repository

import com.example.prova3.model.CommunicationController
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.Location
import com.example.prova3.model.Storage

class GestioneOrdiniRepository {

    data class OrderStatus(
        val stato: String,
        val partenza: Location?,
        val destinazione: Location,
        val drone: Location,
        val tempo: Any // FormattazioneRepository.TimeData o Int (per tempo rimanente)
    )


    suspend fun consegnaInCorso(): Boolean {
        return Storage.inConsegna()
    }

    suspend fun effettuaOrdine(mid: Int): OrderStatus? {
        return try {
            Storage.setConsegna(true)
            println("Acquisto effettuato")

            val ordine = CommunicationController.postOrder(mid)
            if (ordine == null) {
                Storage.setConsegna(false)
                return null
            }

            Storage.setRistorante(mid)
            Storage.setOid(ordine.oid)
            Storage.setMid(mid)

            orderStatus()
        } catch (error: Exception) {
            println("errore in effettuaOrdine: $error")
            Storage.setConsegna(false)
            null
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
    suspend fun orderStatus(): OrderStatus? {
        return try {
            val oid = Storage.getOid()
            println("PRE-->: $oid")
            if (oid == null) return null

            val raw = CommunicationController.getOrderStatus(oid)
            println("POST orderstatus: $raw")
            if (raw == null) return null

            val formattazione = FormattazioneRepository()
            val tempo = if (raw.status == "ON_DELIVERY") {
                raw.expectedDeliveryTimestamp?.let {
                    formattazione.tempoRimanente(it)
                } ?: 0
            } else {
                raw.deliveryTimestamp?.let {
                    formattazione.extractTime(it)
                }
            }

            OrderStatus(
                stato = raw.status,
                partenza = Storage.getRistorante(),
                destinazione = raw.deliveryLocation,
                drone = raw.currentPosition,
                tempo = tempo ?: 0
            )
        } catch (error: Exception) {
            println("errore in orderStatus: $error")
            null
        }
    }

    suspend fun confermaConsegna() {
        Storage.setConsegna(false)
    }
}