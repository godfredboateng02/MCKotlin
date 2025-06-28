package com.example.prova3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.MenuDetailData
import com.example.prova3.repository.GestioneMenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuDetailViewModel(val gestioneMenuRepository: GestioneMenuRepository?) : ViewModel() {

    private val _menuDetail = MutableStateFlow<MenuDetailData?>(null)
    var menuDetail : StateFlow<MenuDetailData?> = _menuDetail

    fun getMenuDetail(mid : Int){
        viewModelScope.launch {
            try {
                _menuDetail.value = gestioneMenuRepository?.menuDetail(56)
                Log.d("MenuDetailViewModel","mid 56 ${menuDetail.value.toString()}")
            }catch (e: Exception){
                Log.d("MenuDetailsViewModel","Error: ${e.message}")
            }
        }
    }
}