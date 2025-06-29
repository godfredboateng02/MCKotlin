package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneAccountRepository.UserData
import com.example.prova3.model.repository.GestioneOrdiniRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(val gestioneAccountRepository: GestioneAccountRepository, val gestioneOrdiniRepository: GestioneOrdiniRepository) : ViewModel() {
    private val _datiUtente = MutableStateFlow<UserData?>(null)
    val datiUtente : StateFlow<UserData?> = _datiUtente
    private val _lastOrderMenu = MutableStateFlow<LastOrderMenu?>(null)
    val lastOrderMenu : StateFlow<LastOrderMenu?> = _lastOrderMenu
    private val _isLoadingUser = MutableStateFlow<Boolean>(false)
    val isLoadingUser : StateFlow<Boolean> = _isLoadingUser
    private val _isLoadingMenu = MutableStateFlow<Boolean>(false)
    val isLoadingMenu : StateFlow<Boolean> = _isLoadingMenu

    fun getUserData(){
        viewModelScope.launch {
            try {
                _isLoadingUser.value = true
                _datiUtente.value = gestioneAccountRepository.getUserData()
                _isLoadingUser.value = false

            }catch (e: Exception){
                Log.d("ProfileViewModel","${e.message}")
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
                Log.d("ProfileViewModel","${e.message}")
                _isLoadingMenu.value = false

            }
        }
    }



}