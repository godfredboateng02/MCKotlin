// GestioneOrdiniRepository.kt
package com.example.prova3.model.repository

import android.util.Log
import com.example.prova3.model.CommunicationController
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.Location
import com.example.prova3.model.Storage
import com.example.prova3.model.TimeData
import com.example.prova3.repository.Formattazione
import kotlin.jvm.Throws

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
                Log.d("OID_ULTIMO","${ordine?.oid ?: "problemone"}")
            Storage.setMid(mid)

            orderStatus()
        } catch (error: Exception) {
            Storage.setConsegna(false)
                throw error
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
            immagine = Formattazione.formatImage(Storage.getImage(mid, menu.imageVersion))
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
            println("POST orderstatus: ${raw.toString()}")
            if (raw == null) {
                return null
            }

           /* Log.d("TEMPO","${raw.expectedDeliveryTimestamp}")
            val tempoRimanente = Formattazione.tempoRimanente(raw.expectedDeliveryTimestamp)
            Log.d("TEMPO","${tempoRimanente}")

            Log.d("TEMPO","${raw.deliveryTimestamp}")
            val orarioConsegna = Formattazione.extractTime(raw.deliveryTimestamp)
            Log.d("TEMPO","${orarioConsegna}")


            Log.d("GestioneOrdini",tempoRimanente.toString())*/

            return OrderStatusCompact(
                stato = raw.status,
                partenza = Storage.getRistorante(),
                destinazione = raw.deliveryLocation,
                drone = raw.currentPosition,
                tempoRimanente = 3,
                orarioConsegna = TimeData("ciao","ciao")
            )
        } catch (error: Exception) {
            throw error
        }
    }

    suspend fun confermaConsegna() {
        Storage.setConsegna(false)
    }
}