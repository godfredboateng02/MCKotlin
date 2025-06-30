package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.MenuDetailData
import com.example.prova3.model.OrderStatus
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneAccountRepository
import com.example.prova3.model.repository.GestioneMenuRepository
import com.example.prova3.model.repository.GestioneOrdiniRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuDetailViewModel(val gestioneMenuRepository: GestioneMenuRepository, val gestioneAccountRepository: GestioneAccountRepository, val gestioneOrdiniRepository: GestioneOrdiniRepository) : ViewModel() {

    private val _menuDetail = MutableStateFlow<MenuDetailData?>(null)
    var menuDetail : StateFlow<MenuDetailData?> = _menuDetail

    private val _isLoading = MutableStateFlow<Boolean>(false)
    var isLoading : StateFlow<Boolean> = _isLoading

    private val _hasCard = MutableStateFlow<Boolean>(false)
    var hasCard : StateFlow<Boolean> = _hasCard

    private val _hasOrder = MutableStateFlow<Boolean>(false)
    var hasOrder : StateFlow<Boolean> = _hasOrder

    private val _done = MutableStateFlow<Boolean>(false)
    val done : StateFlow<Boolean> = _done



    fun hasCard(){
        viewModelScope.launch {
            try {
                val result  = gestioneAccountRepository.getUserData()
                if (result?.carta != null){
                    _hasCard.value = true
                }else{
                    _hasCard.value = false
                }
            }catch (e: Exception){
                Log.d("MenuDetailViewModel","Error: ${e.message}")
            }
        }
    }

    fun hasOrder(){
        viewModelScope.launch {
            _hasOrder.value = gestioneOrdiniRepository.consegnaInCorso()
            Log.d("menudetail","${_hasOrder.value}")
            Log.d("menudetail","${Storage.inConsegna()}")

        }
    }

    fun getMenuDetail(mid : Int){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _menuDetail.value = gestioneMenuRepository.menuDetail(mid)
                Log.d("MenuDetailViewModel","mid 56 ${menuDetail.value.toString()}")
                _isLoading.value = false
            }catch (e: Exception){
                Log.d("MenuDetailsViewModel","Error: ${e.message}")
                _isLoading.value = false
            }
        }
    }





    fun buyMenu(mid : Int){
        viewModelScope.launch {
            if (_hasCard.value && !_hasOrder.value){
                try {
                    gestioneOrdiniRepository.effettuaOrdine(mid)
                    _done.value = true
                    delay(500)
                    _done.value = false
                }catch(e : Exception){
                    Log.d("MenuDetailsViewModel","Error: ${e.message}")
                }
            }

        }
    }
}