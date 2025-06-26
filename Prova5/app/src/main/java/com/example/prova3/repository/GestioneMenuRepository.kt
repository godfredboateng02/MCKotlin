package com.example.prova3.repository

import com.example.prova3.model.CommunicationController
import com.example.prova3.model.Storage

class GestioneMenuRepository {

    data class MenuListItem(
        val mid: Int,
        val nome: String,
        val descrizione: String,
        val prezzo: String,
        val tempo: Int,
        val immagine: String
    )

    data class MenuDetail(
        val nome: String,
        val prezzo: String,
        val descrizione: String,
        val tempo: Int,
        val immagine: String
    )

    // Equivalente di lista() dal JavaScript
    suspend fun lista(): List<MenuListItem> {
        val raw = CommunicationController.getMenus()
        val lista = mutableListOf<MenuListItem>()

        for (element in raw) {
            val immagine = Storage.getImage(element.mid, element.imageVersion)
            lista.add(
                MenuListItem(
                    mid = element.mid,
                    nome = element.name,
                    descrizione = element.shortDescription,
                    prezzo = String.format("%.2f", element.price),
                    tempo = if (element.deliveryTime == 0) 1 else element.deliveryTime,
                    immagine = immagine
                )
            )
        }

        return lista
    }

    // Equivalente di menuDetail() dal JavaScript
    suspend fun menuDetail(mid: Int): MenuDetail {
        val raw = CommunicationController.getMenuDetails(mid)

        return MenuDetail(
            nome = raw.name,
            prezzo = String.format("%.2f", raw.price),
            descrizione = raw.longDescription,
            tempo = if (raw.deliveryTime == 0) 1 else raw.deliveryTime,
            immagine = Storage.getImage(mid, raw.imageVersion)
        )
    }
}
