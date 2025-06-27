package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.repository.GestioneAccountRepository
import com.example.prova3.repository.GestioneAccountRepository.UserData
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _datiUtente = MutableLiveData<UserData>()
    val datiUtente : LiveData<UserData> = _datiUtente

    val gestioneAccount = GestioneAccountRepository()

    fun getUserData(){
        viewModelScope.launch {
            try {
                val result = gestioneAccount.getUserData()
                if (result != null)
                    _datiUtente.postValue(result)
                else
                    Log.d("ProfileViewModel","Error: dati utente null")
            }catch (e: Exception){
                Log.d("ProfileViewModel","${e.message}")
            }
        }
    }
}