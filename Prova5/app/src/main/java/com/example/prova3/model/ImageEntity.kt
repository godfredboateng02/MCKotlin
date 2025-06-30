package com.example.prova3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val mid: Int,
    val version: Int,
    val image: String
)