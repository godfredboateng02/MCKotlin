package com.example.prova3

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
    //var sid : String? = null
    val sid : String = "FmyZjSXI6ghjNDUVLhtl9aQB8zdZAj1hpdX5c7L2d2lpnSJecZQKp1nxzvZ4DY9N"
    val uid : Int = 43841 // Example user ID, replace with actual logic to retrieve or set it

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
        return result
    }

    suspend fun createUser(): UserResponse {
        Log.d(TAG, "createUser")
        val url = BASE_URL+"/user"
        val httpResponse = genericRequest (url, HttpMethod.POST)
        val result : UserResponse = httpResponse.body()
        return result
    }

    suspend fun getUserInfo(): GetUserInfo{
        Log.d(TAG, "getUserInfo")
        val url = BASE_URL+"/user/${uid}"
        val queryParameters = mapOf("sid" to sid)
        val httpResponse = genericRequest(url , HttpMethod.GET, queryParameters = queryParameters)
        val result : GetUserInfo = httpResponse.body()
        return result
    }

    suspend fun putUserInfo(user: PutUserInfo): Unit{
        Log.d(TAG, "putUserInfo: $user")
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
            Log.e(TAG, response.bodyAsText()) // utile per vedere l'errore vero
        }
    }

    suspend fun getMenus(lat: Float, lng: Float): List<Menu>{
        Log.d(TAG, "getMenus")
        val url = BASE_URL+"/menu"
        val httpResponse = genericRequest(
            url = url,
            method = HttpMethod.GET,
            queryParameters = mapOf("sid" to sid,"lat" to lat, "lng" to lng),
        )
        if (!httpResponse.status.isSuccess()){
            Log.e(TAG, "Errore aggiornamento: ${httpResponse.status}")
        }
        val risposta : List<Menu> =  httpResponse.body()
        return risposta
    }

    suspend fun getMenuDetails(mid: Int, lat: Float, lng: Float): MenuDetails{
        Log.d(TAG, "getMenuDetails")
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


    suspend fun postOrder(mid: Int, lat: Float, lng: Float): MenuBuyed? {
        val url  = "$BASE_URL/menu/$mid/buy"
        val body = MenuBuyQuery(sid, Location(lat, lng))

        val resp = genericRequest(url, HttpMethod.POST, requestBody = body)

        if (!resp.status.isSuccess()) {                      // ⬅︎ esci prima
            Log.e(TAG, "HTTP ${resp.status}: ${resp.bodyAsText()}")
            return null                                      // o lancia un'eccezione
        }
        return resp.body()                                   // OK: MenuBuyed
    }

    suspend fun getOrderStatus(oid : Int): OrderStatus?{
        Log.d(TAG,"getOrderStatus")
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
        val risultato : OrderStatus? = httpResponse.body()
        return risultato
    }

    suspend fun deleteLastOrder () : Unit{
        Log.d(TAG, "deleteLastOrder")
        val url = BASE_URL+"/order"
        val httpResponse = genericRequest(url, HttpMethod.DELETE, queryParameters = mapOf("sid" to sid))
        if (!httpResponse.status.isSuccess()){
            Log.d(TAG,"Error: ${httpResponse.status}")
        }
    }

    suspend fun getImage(mid: Int) : Base64image?{
        Log.d(TAG, "getImage")
        val url = BASE_URL+"/menu/${mid}/image"
        val httpResponse = genericRequest(url, HttpMethod.GET, queryParameters = mapOf("sid" to sid, "mid" to mid))
        if(!httpResponse.status.isSuccess()){
            return null
        }
        Log.d(TAG,"immagini a buon fine")
        return httpResponse.body()
    }

}

