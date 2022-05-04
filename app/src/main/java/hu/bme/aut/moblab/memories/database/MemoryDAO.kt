package hu.bme.aut.moblab.memories.database

import androidx.room.*
import hu.bme.aut.moblab.memories.model.db.Memory

@Dao
interface MemoryDAO {
    @Query("SELECT * FROM memory")
    suspend fun getAllMemories(): List<Memory>

    @Insert
    suspend fun insertMemory(memory: Memory): Long

    @Delete
    suspend fun deleteMemory(memory: Memory): Int

    @Update
    suspend fun updateMemory(memory: Memory): Memory
}