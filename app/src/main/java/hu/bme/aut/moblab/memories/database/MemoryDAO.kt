package hu.bme.aut.moblab.memories.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.moblab.memories.model.db.Memory

@Dao
interface MemoryDAO {
    @Query("SELECT * FROM memory")
    fun getAllMemories(): LiveData<List<Memory>>

    @Insert
    suspend fun insertMemory(memory: Memory) : Long

    @Insert
    suspend fun insertMemories(vararg memory: Memory): List<Long>

    @Insert
    suspend fun insertMemoriesList(memories: List<Memory>): List<Long>

    @Delete
    suspend fun deleteMemory(memory: Memory)

    @Query("DELETE FROM memory")
    suspend fun deleteAllMemory()

    @Update
    suspend fun updateMemory(memory: Memory)
}