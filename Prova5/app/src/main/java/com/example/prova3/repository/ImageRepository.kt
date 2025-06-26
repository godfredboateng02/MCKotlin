package com.example.prova3.model

class ImageRepository(private val imageDao: ImageDao) {

    suspend fun getImage(mid: Int, version: Int): String {
        val imageEntity = imageDao.getImageEntity(mid)
        return when {
            imageEntity == null -> "NOT_FOUND"
            imageEntity.version == version -> imageEntity.image
            else -> "VERSION_MISMATCH"
        }
    }

    suspend fun addImage(mid: Int, version: Int, image: String): Boolean {
        return try {
            imageDao.insertImage(ImageEntity(mid, version, image))
            true
        } catch (e: Exception) {
            println("Errore in addImage: ${e.message}")
            throw e
        }
    }

    suspend fun updateImage(mid: Int, version: Int, image: String): Boolean {
        return try {
            imageDao.insertImage(ImageEntity(mid, version, image))
            true
        } catch (e: Exception) {
            println("Errore in updateImage: ${e.message}")
            throw e
        }
    }
}
