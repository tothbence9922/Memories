package hu.bme.aut.moblab.memories.repository

import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.model.dto.toMemory
import hu.bme.aut.moblab.memories.network.MemoriesAPI
import javax.inject.Inject

class MemoryRetrofitRepository @Inject constructor(
    private val memoriesAPI: MemoriesAPI
) {

    suspend fun getAllMemories(): List<MemoryDTO> {
        return memoriesAPI.getMemories()
    }

    suspend fun insert(memoryDTO: MemoryDTO): MemoryDTO {
        return memoriesAPI.createMemory(memoryDTO)
    }

    suspend fun getById(id: String): MemoryDTO {
        return memoriesAPI.getMemoryById(id)
    }

    suspend fun delete(id: String) {
        memoriesAPI.deleteMemory(id)
    }

    suspend fun update(memory: MemoryDTO) {
        memoriesAPI.updateMemory(memory._id!!, MemoryDTO(null, memory.memoryId, memory.title, memory.description, memory.imageUrls, memory.created))
    }
}