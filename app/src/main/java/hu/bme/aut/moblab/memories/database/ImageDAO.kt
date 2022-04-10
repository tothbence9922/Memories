package hu.bme.aut.moblab.memories.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.moblab.memories.model.db.Image

@Dao
interface ImageDAO {
    @Query("SELECT * FROM image")
    fun getAllImages(): LiveData<List<Image>>

    @Insert
    suspend fun insertImage(image: Image) : Long

    @Insert
    suspend fun insertImages(vararg image: Image): List<Long>

    @Insert
    suspend fun insertImagesList(images: List<Image>): List<Long>

    @Delete
    suspend fun deleteImage(image: Image)

    @Query("DELETE FROM image")
    suspend fun deleteAllImage()

    @Update
    suspend fun updateImage(image: Image)
}