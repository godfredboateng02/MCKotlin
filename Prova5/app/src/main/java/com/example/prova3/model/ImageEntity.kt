package com.example.prova3.model  // CAMBIATO DA com.example.prova3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val mid: Int,
    val version: Int,
    val image: String
)