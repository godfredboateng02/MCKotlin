package com.example.prova3.viewmodel

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneAccountRepository.UpdateNameData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

class FirstScreenViewModel(val gestioneAccountRepository: GestioneAccountRepository) : ViewModel(){
    private val _done = MutableStateFlow<Boolean>(false)
    val done : StateFlow<Boolean> = _done

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError : StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage : StateFlow<String> = _errorMessage

    fun updateUserData(nome : String, cognome : String){
        viewModelScope.launch {
            try {
                val user = UpdateNameData(nome, cognome)
                gestioneAccountRepository.updateUserName(user)
                Storage.setPagina("Homepage")
                _done.value = true
                delay(500)
                _done.value = false

                Log.d("ProfileViewModel","Modifica dei dati...${user}")
            }catch (e: Exception){
                _errorMessage.value = "Impossibile creare il profilo"
                _showError.value = true
                Log.d("ProfileViewModel","Error: ${e.message}")
            }
        }
    }
    fun clearError() {
        _showError.value = false
        _errorMessage.value = ""
    }
}