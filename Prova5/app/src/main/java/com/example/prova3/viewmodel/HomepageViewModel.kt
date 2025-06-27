package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.repository.GestioneMenuRepository
import com.example.prova3.repository.GestioneMenuRepository.MenuListItem
import kotlinx.coroutines.launch

class HomepageViewModel : ViewModel(){
    private val _listaMenu = MutableLiveData<List<MenuListItem>>()
    val listaMenu: LiveData<List<MenuListItem>> = _listaMenu

    val rep = GestioneMenuRepository()

    fun getListaMenu(){
        viewModelScope.launch {
            try{
                val result = rep.lista()
                _listaMenu.postValue(result)
            }catch (e: Exception){
                Log.d("HomepageViewModel","Error: ${e.message}")
            }
        }
    }
}