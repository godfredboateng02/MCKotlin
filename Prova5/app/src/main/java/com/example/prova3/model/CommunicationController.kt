package com.example.prova3.model

import android.net.Uri
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object CommunicationController {
    private val BASE_URL = "https://develop.ewlab.di.unimi.it/mc/2425"
    private val TAG = CommunicationController::class.simpleName


    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    enum class HttpMethod {
        GET,
        POST,
        DELETE,
        PUT
    }

    suspend fun genericRequest(url: String, method: HttpMethod,
                               queryParameters: Map<String, Any> = emptyMap(),
                               requestBody: Any? = null) : HttpResponse {

        val urlUri = Uri.parse(url)
        val urlBuilder = urlUri.buildUpon()
        queryParameters.forEach { (key, value) ->
            urlBuilder.appendQueryParameter(key, value.toString())
        }
        val completeUrlString = urlBuilder.build().toString()
        Log.d(TAG, completeUrlString)

        val request: HttpRequestBuilder.() -> Unit = {
            requestBody?.let {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
        }

        val result = when (method) {
            HttpMethod.GET -> client.get(completeUrlString, request)
            HttpMethod.POST -> client.post(completeUrlString, request)
            HttpMethod.DELETE -> client.delete(completeUrlString, request)
            HttpMethod.PUT -> client.put(completeUrlString, request)
        }
        //delay(1000) //DA TOGLIERE POI
        return result
    }

    suspend fun signUp(): UserResponse {
        Log.d(TAG, "createUser")
        val url = BASE_URL+"/user"
        val httpResponse = genericRequest (url, HttpMethod.POST)
        val result : UserResponse = httpResponse.body()
        return result
    }

    // PRENDE uid e sid da Storage automaticamente
    suspend fun getUserInfo(): GetUserInfo{
        Log.d(TAG, "getUserInfo")
        val uid = Storage.getUid()  // Prende da Storage!
        val sid = Storage.getSid()  // Prende da Storage!

        val url = BASE_URL+"/user/${uid}"
        val queryParameters = mapOf("sid" to sid)
        val httpResponse = genericRequest(url , HttpMethod.GET, queryParameters = queryParameters)
        val result : GetUserInfo = httpResponse.body()
        return result
    }

    // PRENDE uid e sid da Storage automaticamente
    suspend fun putUserInfo(user: PutUserInfo): Unit{
        Log.d(TAG, "putUserInfo: $user")
        val uid = Storage.getUid()  // Prende da Storage!
        val sid = Storage.getSid()  // Prende da Storage!

        val url = BASE_URL+"/user/${uid}"
        val response = genericRequest(
            url = url,
            method = HttpMethod.PUT,
            queryParameters = mapOf("sid" to sid),
            requestBody = user
        )

        if (response.status.value in 200..299) {
            Log.d(TAG, "Aggiornamento completato con successo")
        } else {
            Log.e(TAG, "Errore aggiornamento: ${response.status}")
            Log.e(TAG, response.bodyAsText())
        }
    }

    // PRENDE sid e location automaticamente da Storage/LocationManager
    suspend fun getMenus(): List<Menu>{
        Log.d(TAG, "getMenus")
        val sid = Storage.getSid()  // Prende da Storage!
        val lat = LocationManager.getLat()
        val lng = LocationManager.getLng()
        if (lat== null){
            Log.d(TAG, "posizione null")

        }

        val url = BASE_URL+"/menu"
        val httpResponse = genericRequest(
            url = url,
            method = HttpMethod.GET,
            queryParameters = mapOf("sid" to sid,"lat" to lat!!, "lng" to lng!!),
        )
        if (!httpResponse.status.isSuccess()){
            Log.e(TAG, "Errore aggiornamento: ${httpResponse.status}")
        }
        val risposta : List<Menu> =  httpResponse.body()
        return risposta
    }

    // COME JAVASCRIPT: solo mid come parametro, resto automatico!
    suspend fun getMenuDetails(mid: Int): MenuDetails{
        Log.d(TAG, "getMenuDetails")
        val sid = Storage.getSid()  // Prende da Storage!
        val lat = LocationManager.getLat() ?: 0.0f
        val lng = LocationManager.getLng() ?: 0.0f

        val url = BASE_URL+"/menu/${mid}"
        val httpResponse = genericRequest(
            url = url,
            method = HttpMethod.GET,
            queryParameters = mapOf("sid" to sid, "lat" to lat, "lng" to lng, "mid" to mid)
        )
        if (!httpResponse.status.isSuccess()){
            Log.e(TAG, "Errore aggiornamento: ${httpResponse.status}")
        }
        val risultato : MenuDetails = httpResponse.body()
        return risultato
    }

    // PRENDE automaticamente sid e location da Storage/LocationManager
    suspend fun postOrder(mid: Int): MenuBuyed? {
        val sid = Storage.getSid()  // Prende da Storage!
        val lat = LocationManager.getLat() ?: 0.0f
        val lng = LocationManager.getLng() ?: 0.0f

        val url  = "$BASE_URL/menu/$mid/buy"
        val body = MenuBuyQuery(sid, Location(lat, lng))

        val resp = genericRequest(url, HttpMethod.POST, requestBody = body)

        if (!resp.status.isSuccess()) {
            Log.e(TAG, "HTTP ${resp.status}: ${resp.bodyAsText()}")
            return null
        }
        return resp.body()
    }

    // COME JAVASCRIPT: solo mid come parametro, sid automatico!
    suspend fun getMenuImage(mid: Int): Image{
        Log.d(TAG, "getMenuImage")
        val sid = Storage.getSid()  // Prende da Storage!

        val url = "$BASE_URL/menu/$mid/image"
        val httpResponse = genericRequest(
            url = url,
            method = HttpMethod.GET,
            queryParameters = mapOf("sid" to sid, "mid" to mid)
        )
        if (!httpResponse.status.isSuccess()){
            Log.e(TAG, "Errore aggiornamento: ${httpResponse.status}")
        }
        val risultato : Image = httpResponse.body()
        return risultato
    }

    // PRENDE automaticamente sid da Storage
    suspend fun getOrderStatus(oid : Int): OrderStatus?{
        Log.d(TAG,"getOrderStatus")
        val sid = Storage.getSid()  // Prende da Storage!

        val url = BASE_URL+"/order/$oid"
        val httpResponse = genericRequest(
            url = url,
            method = HttpMethod.GET,
            queryParameters = mapOf("sid" to sid,"oid" to oid)
        )

        if (!httpResponse.status.isSuccess()){
            Log.d(TAG, "Error ${httpResponse.status}")
            return null
        }
        Log.d(TAG,"${httpResponse}")

        val risultato : OrderStatus? = httpResponse.body()
        return risultato
    }

    // PRENDE automaticamente sid da Storage
    suspend fun deleteLastOrder () : Unit{
        Log.d(TAG, "deleteLastOrder")
        val sid = Storage.getSid()  // Prende da Storage!

        val url = BASE_URL+"/order"
        val httpResponse = genericRequest(url, HttpMethod.DELETE, queryParameters = mapOf("sid" to sid))
        if (!httpResponse.status.isSuccess()){
            Log.d(TAG,"Error: ${httpResponse.status}")
        }
    }
}