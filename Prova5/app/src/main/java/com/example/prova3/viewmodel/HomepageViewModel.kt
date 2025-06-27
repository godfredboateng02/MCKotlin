package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.repository.GestioneMenuRepository
import com.example.prova3.repository.GestioneMenuRepository.MenuListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(val gestioneMenuRepository: GestioneMenuRepository) : ViewModel(){
    private val _listaMenu = MutableStateFlow<List<MenuListItem>?>(null)
    val listaMenu: StateFlow<List<MenuListItem>?> = _listaMenu


    fun getListaMenu(){
        viewModelScope.launch {
            try{
                _listaMenu.value = gestioneMenuRepository.lista()
            }catch (e: Exception){
                Log.d("HomepageViewModel","Error: ${e.message}")
            }
        }
    }
}