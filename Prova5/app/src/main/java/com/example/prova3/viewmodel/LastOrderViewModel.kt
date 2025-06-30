package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.repository.GestioneOrdiniRepository
import com.example.prova3.model.repository.GestioneOrdiniRepository.OrderStatusCompact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class LastOrderViewModel(val gestioneOrdiniRepository: GestioneOrdiniRepository) : ViewModel() {

    private val _lastOrderMenu = MutableStateFlow<LastOrderMenu?>(null)
    val lastOrderMenu : StateFlow<LastOrderMenu?> = _lastOrderMenu

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _orderStatus = MutableStateFlow<OrderStatusCompact?>(null)
    val orderStatus : StateFlow<OrderStatusCompact?> = _orderStatus

    fun lastOrderMenu(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _lastOrderMenu.value = gestioneOrdiniRepository.lastOrderMenu()
                _isLoading.value = false

            }catch (e: Exception){
                Log.d("LastOrderView","${e.message}")
                _isLoading.value = false

            }
        }
    }

    fun getOrderStatus(){
        viewModelScope.launch {
            try {
                _orderStatus.value = gestioneOrdiniRepository.orderStatus()
                Log.d("LastOrderView","Stato recuperato...")
            }catch (e: Exception){
                Log.d("LastOrderView","Error: ${e.message}")
            }
        }
    }

    fun confermaRicezione(){
        viewModelScope.launch {
            try {
                gestioneOrdiniRepository.confermaConsegna()
            }catch(e: Exception){
                Log.d("LastOrderViewModel","${e.message}")
            }
        }
    }
}