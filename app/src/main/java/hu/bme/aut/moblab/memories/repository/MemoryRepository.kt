package hu.bme.aut.moblab.memories.repository

import androidx.lifecycle.LiveData
import hu.bme.aut.moblab.memories.database.MemoryDAO
import hu.bme.aut.moblab.memories.model.db.Memory

class MemoryRepository(private val memoryDao: MemoryDAO) {

    suspend fun getAllMemories(): List<Memory> {
        return memoryDao.getAllMemories()
    }

    suspend fun insert(memory: Memory) {
        memoryDao.insertMemory(memory)
    }

    suspend fun delete(memory: Memory) {
        memoryDao.deleteMemory(memory)
    }
}