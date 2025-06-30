package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.TimeData
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneAccountRepository.UserData
import com.example.prova3.model.repository.GestioneOrdiniRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Time

class ProfileViewModel(val gestioneAccountRepository: GestioneAccountRepository, val gestioneOrdiniRepository: GestioneOrdiniRepository) : ViewModel() {
    private val _datiUtente = MutableStateFlow<UserData?>(null)
    val datiUtente : StateFlow<UserData?> = _datiUtente
    private val _lastOrderMenu = MutableStateFlow<LastOrderMenu?>(null)
    val lastOrderMenu : StateFlow<LastOrderMenu?> = _lastOrderMenu
    private val _isLoadingUser = MutableStateFlow<Boolean>(false)
    val isLoadingUser : StateFlow<Boolean> = _isLoadingUser
    private val _isLoadingMenu = MutableStateFlow<Boolean>(false)
    val isLoadingMenu : StateFlow<Boolean> = _isLoadingMenu
    private val _lastOrderTime = MutableStateFlow<TimeData?>(null)
    val lastOrderTime : StateFlow<TimeData?> = _lastOrderTime

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError : StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage : StateFlow<String> = _errorMessage

    fun getUserData(){
        viewModelScope.launch {
            try {
                _isLoadingUser.value = true
                delay(500)
                _datiUtente.value = gestioneAccountRepository.getUserData()
                _isLoadingUser.value = false

            }catch (e: Exception){
                Log.d("ProfileViewModel","${e.message}")
                _errorMessage.value = "Impossibile ottenere i dati dell'utente"
                _showError.value = true
                _isLoadingUser.value = false

            }
        }
    }

    fun lastOrderMenu(){
        viewModelScope.launch {
            try {
                _isLoadingMenu.value = true
                _lastOrderMenu.value = gestioneOrdiniRepository.lastOrderMenu()
                _isLoadingMenu.value = false

            }catch (e: Exception){
                _errorMessage.value = "Impossibile recuperare i dati del ultimo ordine"
                _showError.value = true
                Log.d("ProfileViewModel","${e.message}")
                _isLoadingMenu.value = false

            }
        }
    }

    fun lastOrderTime(){
        viewModelScope.launch {
            try {
                _lastOrderTime.value = gestioneAccountRepository.lastOrderTime()
            }catch (e : Exception){
                Log.d("ProfileViewModel","${e.message}")
            }
        }
    }

    fun clearError() {
        _showError.value = false
        _errorMessage.value = ""
    }



}