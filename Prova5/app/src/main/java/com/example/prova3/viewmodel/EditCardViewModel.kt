package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.repository.GestioneAccountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditCardViewModel(val gestioneAccountRepository: GestioneAccountRepository) : ViewModel() {
    private val _done = MutableStateFlow<Boolean>(false)
    val done : StateFlow<Boolean> = _done


    fun updateCardData(fullName : String, number : String, expireMonth : Int, expireYear : Int, cvv : String ){
        viewModelScope.launch {
            try {
                gestioneAccountRepository.updateUserCard(fullName, number, expireMonth, expireYear, cvv)
                _done.value = true
                delay(500)
                _done.value = false
            }catch (e: Exception){
                Log.d("ProfileViewModel","Error: ${e.message}")
            }
        }
    }

}