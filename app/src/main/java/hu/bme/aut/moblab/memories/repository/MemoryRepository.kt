package hu.bme.aut.moblab.memories.repository

import hu.bme.aut.moblab.memories.database.MemoryDAO
import hu.bme.aut.moblab.memories.model.db.Memory
import javax.inject.Inject

class MemoryRepository @Inject constructor(private val memoryDao: MemoryDAO) {

    suspend fun getAllMemories() : List<Memory> {
        return memoryDao.getAll()
    }

    suspend fun insert(memory: Memory) {
        memoryDao.insert(memory)
    }

    suspend fun getById(id: String) : Memory {
        return memoryDao.getById(id)
    }

    suspend fun delete(memory: Memory) {
        memoryDao.delete(memory)
    }

    suspend fun update(memory: Memory) {
        memoryDao.update(memory)
    }
}