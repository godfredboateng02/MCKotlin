package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.MenuListItem
import com.example.prova3.model.Storage
import com.example.prova3.model.repository.GestioneMenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(val gestioneMenuRepository: GestioneMenuRepository) : ViewModel(){
    private val _listaMenu = MutableStateFlow<List<MenuListItem>?>(null)
    val listaMenu: StateFlow<List<MenuListItem>?> = _listaMenu
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _consegnaInCorso = MutableStateFlow<Boolean>(false)
    val consegnaInCorso : StateFlow<Boolean> = _consegnaInCorso

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError : StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage : StateFlow<String> = _errorMessage

    fun checkConsegnaInCorso(){
        viewModelScope.launch {
            _consegnaInCorso.value = Storage.inConsegna()
        }
    }

    fun getListaMenu(){
        viewModelScope.launch {
            try{
                _isLoading.value = true
                _listaMenu.value = gestioneMenuRepository.lista()
                _isLoading.value = false
            }catch (e: Exception){
                Log.d("HomepageViewModel","Error: ${e.message}")
                _errorMessage.value = "Impossibile ottenere la lista dei menu"
                _showError.value = true
                _isLoading.value = false

            }
        }
    }
    fun clearError() {
        _showError.value = false
        _errorMessage.value = ""
    }
}