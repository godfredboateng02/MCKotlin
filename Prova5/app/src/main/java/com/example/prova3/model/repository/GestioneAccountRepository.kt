// GestioneAccountRepository.kt
package com.example.prova3.model.repository

import com.example.prova3.model.CommunicationController
import com.example.prova3.model.PutUserInfo
import com.example.prova3.model.Storage
import com.example.prova3.model.TimeData
import com.example.prova3.repository.Formattazione

class GestioneAccountRepository {

    data class UserData(
        val nome: String?,
        val cognome: String?,
        val carta: CartaData?
    )

    data class CartaData(
        val titolare: String,
        val numero: String, // Ultime 4 cifre
        val mese: Int,
        val anno: Int
    )



    data class UpdateNameData(
        val nome: String,
        val cognome: String
    )

    // Equivalente di getUserData() dal JavaScript
    suspend fun getUserData(): UserData? {
        return try {
            val risposta = CommunicationController.getUserInfo()

            val carta = if (risposta.cardFullName != null) {
                CartaData(
                    titolare = risposta.cardFullName,
                    numero = risposta.cardNumber?.takeLast(4) ?: "",
                    mese = risposta.cardExpireMonth ?: 0,
                    anno = risposta.cardExpireYear ?: 0
                )
            } else null

            UserData(
                nome = risposta.firstName,
                cognome = risposta.lastName,
                carta = carta
            )
        } catch (error: Exception) {
            throw error
        }
    }

    // Equivalente di updateUserCard() dal JavaScript
    suspend fun updateUserCard(titolare: String, numero: String, mese: Int, anno: Int, cvv : String) {
        val identity = getUserData()

        val bodyParams = PutUserInfo(
            firstName = identity?.nome,
            lastName = identity?.cognome,
            cardFullName = titolare,
            cardNumber = numero,
            cardExpireMonth = mese,
            cardExpireYear = anno,
            cardCVV = cvv,
            sid = Storage.getSid()
        )

        CommunicationController.putUserInfo(bodyParams)
    }

    // Equivalente di updateUserName() dal JavaScript
    suspend fun updateUserName(nameData: UpdateNameData) {
        val dati = CommunicationController.getUserInfo()

        val bodyParams = PutUserInfo(
            firstName = nameData.nome,
            lastName = nameData.cognome,
            cardFullName = dati.cardFullName,
            cardNumber = dati.cardNumber,
            cardExpireMonth = dati.cardExpireMonth,
            cardExpireYear = dati.cardExpireYear,
            cardCVV = dati.cardCVV,
            sid = Storage.getSid()
        )

        CommunicationController.putUserInfo(bodyParams)
    }

    // Equivalente di lastOrderTime() dal JavaScript
    suspend fun lastOrderTime(): TimeData? {
        println("entrato in lastOrderTime")

        return try {
            val oid = Storage.getOid()
            println("oid da storage: $oid")
            if (oid == null) return null

            val risposta = CommunicationController.getOrderStatus(oid)
            println("risposta: $risposta")
            if (risposta == null) return null

            val timeData = Formattazione.extractTime(risposta.creationTimestamp)

            timeData
        } catch (error: Exception) {
            throw error
        }
    }}