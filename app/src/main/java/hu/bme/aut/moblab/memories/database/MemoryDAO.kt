package hu.bme.aut.moblab.memories.database

import androidx.room.*
import hu.bme.aut.moblab.memories.model.db.Memory

@Dao
interface MemoryDAO {
    @Query("SELECT * FROM memory")
    suspend fun getAll(): List<Memory>
    @Query("SELECT * FROM memory WHERE memoryId = :id")
    suspend fun getById(id: String): Memory
    @Insert
    suspend fun insert(memory: Memory)
    @Update
    suspend fun update(memory: Memory): Void
    @Delete
    suspend fun delete(memory: Memory)
}