package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.repository.GestioneAccountRepository
import kotlinx.coroutines.launch

class EditCardViewModel(val gestioneAccountRepository: GestioneAccountRepository) : ViewModel() {



    fun updateCardData(fullName : String, number : String, expireMonth : Int, expireYear : Int, cvv : String ){
        viewModelScope.launch {
            try {
                gestioneAccountRepository.updateUserCard(fullName, number, expireMonth, expireYear, cvv)
            }catch (e: Exception){
                Log.d("ProfileViewModel","Error: ${e.message}")
            }
        }
    }

}