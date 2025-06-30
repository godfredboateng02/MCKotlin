package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.LastOrderMenu
import com.example.prova3.model.Location
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneOrdiniRepository
import com.example.prova3.model.repository.GestioneOrdiniRepository.OrderStatusCompact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class DeliveryViewModel(val gestioneOrdiniRepository: GestioneOrdiniRepository) : ViewModel() {

    private val _lastOrderMenu = MutableStateFlow<LastOrderMenu?>(null)
    val lastOrderMenu : StateFlow<LastOrderMenu?> = _lastOrderMenu

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _orderStatus = MutableStateFlow<OrderStatusCompact?>(null)
    val orderStatus : StateFlow<OrderStatusCompact?> = _orderStatus

    private val _ristorante = MutableStateFlow<Location?>(null)
    val ristorante : StateFlow<Location?> = _ristorante

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError : StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage : StateFlow<String> = _errorMessage

    fun lastOrderMenu(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _lastOrderMenu.value = gestioneOrdiniRepository.lastOrderMenu()
                _isLoading.value = false

            }catch (e: Exception){
                _errorMessage.value = "Impossibile caricare dettagli menu"
                _showError.value = true
                Log.d("LastOrderView","${e.message}")
                _isLoading.value = false

            }
        }
    }

    fun getOrderStatus(){
        viewModelScope.launch {
            _ristorante.value = Storage.getRistorante()
            try {
                do {
                    _orderStatus.value = gestioneOrdiniRepository.orderStatus()
                    Log.d("LastOrderView","Stato recuperato...")
                    delay(5000)
                } while (_orderStatus.value?.stato == "ON_DELIVERY")

            }catch (e: Exception){
                Log.d("LastOrderView","Error: ${e.message}")
                _errorMessage.value = "Impossibile ottenere lo stato del ordine"
                _showError.value = true
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
    fun clearError() {
        _showError.value = false
        _errorMessage.value = ""
    }
}
