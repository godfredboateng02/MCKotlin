package com.example.prova3.model

import androidx.room.*

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE mid = :mid")
    suspend fun getImageEntity(mid: Int): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)
}
