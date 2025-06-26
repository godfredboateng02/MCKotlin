package com.example.prova3

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationManager {

    // Context applicazione
    private lateinit var appContext: Context
    private var fusedLocationClient: FusedLocationProviderClient? = null

    // Cache come nel JavaScript
    private var cachedLat: Float? = null
    private var cachedLng: Float? = null
    private var resetJob: Job? = null

    // INIZIALIZZAZIONE - semplice come Storage
    fun initialize(context: Context) {
        if (!::appContext.isInitialized) {
            appContext = context.applicationContext
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
        }
    }

    // Verifica inizializzazione
    private fun checkInitialized() {
        if (!::appContext.isInitialized) {
            throw IllegalStateException("LocationManager.initialize(context) non chiamato!")
        }
    }

    // Verifica se abbiamo i permessi per la posizione
    private fun hasLocationPermission(): Boolean {
        checkInitialized()
        return ActivityCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Equivalente di getCurrentPosition() del JavaScript
    suspend fun getCurrentPosition(): LocationData? {
        return try {
            if (!hasLocationPermission()) {
                println("Permessi non concessi per accedere alla posizione.")
                return null
            }

            val location = getLastKnownLocation()
            if (location != null) {
                LocationData(
                    latitude = location.latitude.toFloat(),
                    longitude = location.longitude.toFloat()
                )
            } else {
                null
            }
        } catch (error: Exception) {
            println("Errore durante l'acquisizione della posizione: ${error.message}")
            null
        }
    }

    // Ottiene l'ultima posizione conosciuta
    @Suppress("MissingPermission")
    private suspend fun getLastKnownLocation(): Location? {
        checkInitialized()
        return suspendCoroutine { continuation ->
            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                ?.addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }

    // Sleep function (equivalente del JavaScript)
    private suspend fun sleep(ms: Long) {
        delay(ms)
    }

    // Reset della cache dopo 60 secondi (come nel JavaScript)
    private fun reset() {
        resetJob?.cancel()
        resetJob = CoroutineScope(Dispatchers.IO).launch {
            sleep(60000) // 60 secondi
            cachedLat = null
            cachedLng = null
        }
    }

    // Aggiorna la posizione (equivalente di updateLocation)
    private suspend fun updateLocation() {
        val position = getCurrentPosition()
        if (position != null) {
            cachedLat = position.latitude
            cachedLng = position.longitude
            reset() // Avvia il timer di reset
        }
    }

    // Equivalente di getLat(RN) del JavaScript
    suspend fun getLat(forceRefresh: Boolean = false): Float? {
        checkInitialized()
        if (cachedLat == null || forceRefresh) {
            updateLocation()
        }
        println("getLat: $cachedLat")
        return cachedLat
    }

    // Equivalente di getLng(RN) del JavaScript
    suspend fun getLng(forceRefresh: Boolean = false): Float? {
        checkInitialized()
        if (cachedLng == null || forceRefresh) {
            updateLocation()
        }
        println("getLng: $cachedLng")
        return cachedLng
    }

    // Pulisce i job quando non servono più
    fun cleanup() {
        resetJob?.cancel()
    }
}

// Data class per i dati di posizione (compatibile con DataClasses.kt)
data class LocationData(
    val latitude: Float,
    val longitude: Float
)