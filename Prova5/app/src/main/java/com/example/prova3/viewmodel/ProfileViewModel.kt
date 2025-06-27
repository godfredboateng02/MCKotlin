package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.repository.GestioneAccountRepository
import com.example.prova3.repository.GestioneAccountRepository.UpdateNameData
import com.example.prova3.repository.GestioneAccountRepository.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(val gestioneAccountRepository: GestioneAccountRepository) : ViewModel() {
    private val _datiUtente = MutableStateFlow<UserData?>(null)
    val datiUtente : StateFlow<UserData?> = _datiUtente

    fun getUserData(){
        viewModelScope.launch {
            try {
                _datiUtente.value = gestioneAccountRepository.getUserData()

            }catch (e: Exception){
                Log.d("ProfileViewModel","${e.message}")
            }
        }
    }

    fun updateUserData(user: UpdateNameData){
        viewModelScope.launch {
            try {
                gestioneAccountRepository.updateUserName(user)
                Log.d("ProfileViewModel","Modifica dei dati...")
            }catch (e: Exception){
                Log.d("ProfileViewModel","Error: ${e.message}")
            }
        }
    }

}