package com.example.prova3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prova3.repository.GestioneMenuRepository.MenuListItem
import com.example.prova4.components.MenuElement


@Composable
fun MenuListView(l :List<MenuListItem>){

    //val list = l ?: emptyList()

    /*val rows: List<List<Menu>> = list.orEmpty().chunked(2)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(rows) { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                pair.forEach { menu ->
                    MenuElement(menu)
                }
                // se la riga ha un solo elemento, aggiungo uno Spacer “placeholder”:
                if (pair.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }*/
    val rows: List<List<MenuListItem>> = l.chunked(2)

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy (16.dp)
    ) {
        items(rows){pair ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ){
                pair.forEach { menu ->
                    MenuElement(menu)
                }
                // se la riga ha un solo elemento, aggiungo uno Spacer “placeholder”:
                if (pair.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }

        }
    }

}