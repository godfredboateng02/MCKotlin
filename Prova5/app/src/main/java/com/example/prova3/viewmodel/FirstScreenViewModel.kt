package com.example.prova3.viewmodel

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneAccountRepository.UpdateNameData

import kotlinx.coroutines.launch

class FirstScreenViewModel(val gestioneAccountRepository: GestioneAccountRepository) : ViewModel(){
    fun updateUserData(nome : String, cognome : String){
        viewModelScope.launch {
            try {
                val user = UpdateNameData(nome, cognome)
                gestioneAccountRepository.updateUserName(user)
                Storage.setPagina("Homepage")
                Log.d("ProfileViewModel","Modifica dei dati...${user}")
            }catch (e: Exception){
                Log.d("ProfileViewModel","Error: ${e.message}")
            }
        }
    }
}