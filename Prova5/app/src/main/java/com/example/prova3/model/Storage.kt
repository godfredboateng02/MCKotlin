package com.example.prova3.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Extension per creare il DataStore - applicata al Context
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

object Storage {

    // Context applicazione - DEVE essere inizializzato
    private lateinit var appContext: Context

    // Keys per DataStore - TIPI COERENTI con DataClasses.kt
    private object PreferencesKeys {
        val SID = stringPreferencesKey("sid")
        val UID = intPreferencesKey("uid")
        val OID = intPreferencesKey("oid")
        val MID = intPreferencesKey("mid")
        val PAGINA = stringPreferencesKey("pagina")
        val PARAMETRI = stringPreferencesKey("parametri")
        val RISTORANTE_LAT = floatPreferencesKey("ristorante_lat")
        val RISTORANTE_LNG = floatPreferencesKey("ristorante_lng")
        val CONSEGNA = booleanPreferencesKey("consegna")
    }


    // Cache CONDIVISA - UNA SOLA ISTANZA per tutta l'app!
    private var cachedSid: String? = null
    private var cachedUid: Int? = null
    private var cachedOid: Int? = null
    private var cachedMid: Int? = null
    private var cachedPagina: String? = null
    private var cachedParametri: String? = null
    private var cachedRistorante: Location? = null
    private var cachedConsegna: Boolean? = null

    // Database - inizializzato quando serve (non lazy)
    private fun getDatabase(): AppDatabase {
        checkInitialized()
        return AppDatabase.getDatabase(appContext)
    }

    private fun getImageDao(): ImageDao {
        return getDatabase().imageDao()
    }

    // INIZIALIZZAZIONE - serve solo per il Context
    fun initialize(context: Context) {
        if (!::appContext.isInitialized) {
            appContext = context.applicationContext
        }
    }

    // Verifica inizializzazione
    private fun checkInitialized() {
        if (!::appContext.isInitialized) {
            throw IllegalStateException("Storage.initialize(context) non chiamato!")
        }
    }

    // ===== METODI IDENTICI AL JAVASCRIPT =====


    //TODO: se non è più la prima volta che logghiamo sull'app allora non mandare la firstScreen ma la pagina principale
    suspend fun getPagina(): String {
        checkInitialized()
        if (cachedPagina == null) {
            val prefs = appContext.dataStore.data.first()
            cachedPagina = prefs[PreferencesKeys.PAGINA] ?: "FirstScreen"
        }
        return cachedPagina!!
    }

    suspend fun setPagina(pagina: String) {
        checkInitialized()
        cachedPagina = pagina
        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PAGINA] = pagina
        }
    }

    suspend fun getParametri(): String? {
        checkInitialized()
        if (cachedParametri == null) {
            val prefs = appContext.dataStore.data.first()
            cachedParametri = prefs[PreferencesKeys.PARAMETRI]
        }
        return cachedParametri
    }

    suspend fun setParametri(parametri: String) {
        checkInitialized()
        cachedParametri = parametri
        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PARAMETRI] = parametri
        }
    }

    suspend fun getSid(): String {
        checkInitialized()
        if (cachedSid != null) {
            return cachedSid!!
        }

        val prefs = appContext.dataStore.data.first()
        val storedSid = prefs[PreferencesKeys.SID]

        if (storedSid != null) {
            cachedSid = storedSid
            return cachedSid!!
        }

        registrazione()
        return getSid()
    }

    suspend fun getUid(): Int {
        checkInitialized()
        if (cachedUid != null) {
            Log.d("UID",cachedUid.toString())
            return cachedUid!!
        }

        val prefs = appContext.dataStore.data.first()
        val storedUid = prefs[PreferencesKeys.UID]

        if (storedUid != null) {
            cachedUid = storedUid
            Log.d("UID",cachedUid.toString())
            return cachedUid!!
        }

        registrazione()
        return getUid()
    }

    private suspend fun registrazione() {
        val credenziali: UserResponse = CommunicationController.signUp()
        cachedSid = credenziali.sid
        cachedUid = credenziali.uid

        println("registrazione: ${cachedUid}, ${cachedSid}")

        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SID] = cachedSid!!
            preferences[PreferencesKeys.UID] = cachedUid!!
        }
    }

    suspend fun getOid(): Int? {
        checkInitialized()
        if (cachedOid == null) {
            val prefs = appContext.dataStore.data.first()
            cachedOid = prefs[PreferencesKeys.OID]
        }
        return cachedOid
    }

    suspend fun setOid(oid: Int) {
        checkInitialized()
        cachedOid = oid
        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.OID] = oid
        }
    }

    suspend fun getMid(): Int? {
        checkInitialized()
        if (cachedMid == null) {
            val prefs = appContext.dataStore.data.first()
            cachedMid = prefs[PreferencesKeys.MID]
        }
        return cachedMid
    }

    suspend fun setMid(mid: Int) {
        checkInitialized()
        cachedMid = mid
        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.MID] = mid
        }
    }

    suspend fun getRistorante(): Location? {
        checkInitialized()
        if (cachedRistorante == null) {
            val prefs = appContext.dataStore.data.first()
            val lat = prefs[PreferencesKeys.RISTORANTE_LAT]
            val lng = prefs[PreferencesKeys.RISTORANTE_LNG]

            if (lat != null && lng != null) {
                cachedRistorante = Location(lat, lng)
            }
        }
        return cachedRistorante
    }

    suspend fun setRistorante(mid: Int) {
        checkInitialized()
        val lat = LocationManager.getLat() ?: 0.0f
        val lng = LocationManager.getLng() ?: 0.0f

        val risposta: MenuDetails = CommunicationController.getMenuDetails(mid)
        val ristorante: Location = risposta.location

        cachedRistorante = ristorante
        println("setRistorante lat,lng: ${ristorante.lat} ${ristorante.lng}")

        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.RISTORANTE_LAT] = ristorante.lat ?: 0.0f
            preferences[PreferencesKeys.RISTORANTE_LNG] = ristorante.lng ?: 0.0f
        }
    }

    private fun getImageRepository(): ImageRepository {
        return ImageRepository(getImageDao())
    }

    suspend fun getImage(mid: Int, version: Int): String {
        val repository = getImageRepository()
        val result = repository.getImage(mid, version)

        return when (result) {
            "NOT_FOUND" -> {
                val raw = CommunicationController.getMenuImage(mid)
                val image = raw.base64
                println("presa da rete")
                repository.addImage(mid, version, image)
                image
            }
            "VERSION_MISMATCH" -> {
                val raw = CommunicationController.getMenuImage(mid)
                val image = raw.base64
                println("presa da rete - aggiornamento versione")
                repository.updateImage(mid, version, image)
                image
            }
            else -> result
        }
    }

    suspend fun inConsegna(): Boolean {
        checkInitialized()
        if (cachedConsegna == null) {
            val prefs = appContext.dataStore.data.first()
            cachedConsegna = prefs[PreferencesKeys.CONSEGNA] ?: false
        }
        return cachedConsegna!!
    }

    suspend fun setConsegna(consegna: Boolean) {
        checkInitialized()
        cachedConsegna = consegna
        appContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CONSEGNA] = consegna
        }
    }
}

/*
UTILIZZO IDENTICO AL JAVASCRIPT:

// Inizializzazione (una volta sola):
Storage.initialize(context)

// Uso (ovunque nell'app):
val sid = Storage.getSid()        // Esattamente come JS!
val pagina = Storage.getPagina()  // Esattamente come JS!
Storage.setPagina("MenuScreen")   // Esattamente come JS!
*/