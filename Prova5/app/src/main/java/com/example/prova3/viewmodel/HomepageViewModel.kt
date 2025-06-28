package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.MenuListItem
import com.example.prova3.repository.GestioneMenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(val gestioneMenuRepository: GestioneMenuRepository) : ViewModel(){
    private val _listaMenu = MutableStateFlow<List<MenuListItem>?>(null)
    val listaMenu: StateFlow<List<MenuListItem>?> = _listaMenu
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun getListaMenu(){
        viewModelScope.launch {
            try{
                _isLoading.value = true
                _listaMenu.value = gestioneMenuRepository.lista()
                _isLoading.value = false
            }catch (e: Exception){
                Log.d("HomepageViewModel","Error: ${e.message}")
                _isLoading.value = false

            }
        }
    }
}