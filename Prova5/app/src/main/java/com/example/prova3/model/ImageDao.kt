package com.example.prova3

import androidx.room.*

@Dao
interface ImageDao {

    @Query("SELECT * FROM images WHERE mid = :mid")
    suspend fun getImageEntity(mid: Int): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)

    suspend fun getImage(mid: Int, version: Int): String {
        val imageEntity = getImageEntity(mid)
        return when {
            imageEntity == null -> "NOT_FOUND"
            imageEntity.version == version -> imageEntity.image
            else -> "VERSION_MISMATCH"
        }
    }

    suspend fun addImage(mid: Int, version: Int, image: String): Boolean {
        return try {
            insertImage(ImageEntity(mid, version, image))
            true
        } catch (e: Exception) {
            println("Errore in addImage: ${e.message}")
            throw e
        }
    }

    suspend fun updateImage(mid: Int, version: Int, image: String): Boolean {
        return try {
            insertImage(ImageEntity(mid, version, image))
            true
        } catch (e: Exception) {
            println("Errore in updateImage: ${e.message}")
            throw e
        }
    }
}