package com.example.prova3.model

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64

@Serializable
data class UserResponse(
    val sid: String,
    val uid: Int
)

@Serializable
data class ResponseError(
    val error: String
)

@Serializable
data class GetUserInfo(
    val firstName: String?,
    val lastName: String?,
    val cardFullName: String?,
    val cardNumber: String?,
    val cardExpireMonth: Int?,
    val cardExpireYear: Int?,
    val cardCVV: String?,
    val uid: Int,
    val lastOid: Int?,
    val orderStatus: String?
)

@Serializable
data class PutUserInfo(
    val firstName: String?,
    val lastName: String?,
    val cardFullName: String?,
    val cardNumber: String?,
    val cardExpireMonth: Int?,
    val cardExpireYear: Int?,
    val cardCVV: String?,
    val sid: String
)

@Serializable
data class Image(
    val base64: String
)

@Serializable
data class Menu(
    val mid: Int,
    val name: String,
    val price: Float,
    val location: Location,
    val imageVersion: Int,
    val shortDescription: String,
    val deliveryTime: Int
)

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)

@Serializable
data class MenuDetails (
    val mid: Int,
    val name: String,
    val price: Float,
    val location: Location,
    val imageVersion: Int,
    val shortDescription: String,
    val deliveryTime: Int,
    val longDescription: String,
)

@Serializable
data class MenuBuyed(
    val oid:  Int,
    val mid:  Int,
    val uid:  Int,
    val creationTimestamp: String,
    val status: String,
    val deliveryLocation: Location,
    val expectedDeliveryTimestamp: String,
    val currentPosition: Location
)

@Serializable
data class MenuBuyQuery (
    val sid: String,
    val deliveryLocation: Location
)

@Serializable
data class OrderStatus(
    val oid: Int,
    val mid: Int,
    val uid: Int,
    val creationTimestamp: String,
    val status: String,
    val deliveryLocation: Location,

    val deliveryTimestamp: String? = null,
    val expectedDeliveryTimestamp: String? = null,
    val currentPosition: Location

)

data class LastOrderMenu(
    val nome: String,
    val prezzo: String,
    val descrizione: String,
    val immagine: ImageBitmap?
)
data class MenuDetailData(
    val nome: String,
    val prezzo: String,
    val descrizione: String,
    val tempo: Int,
    val immagine: ImageBitmap?
)

data class MenuListItem(
    val mid: Int,
    val nome: String,
    val descrizione: String,
    val prezzo: String,
    val tempo: Int,
    val immagine: ImageBitmap?
)

data class TimeData(
    val data: String,
    val ora: String
)
