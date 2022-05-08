package hu.bme.aut.moblab.memories.bl.offline

import hu.bme.aut.moblab.memories.repository.MemoryRepository
import javax.inject.Inject

class GetMemoryById @Inject constructor(
    private val memoryRepository: MemoryRepository
) {

    suspend operator fun invoke(id: String) = memoryRepository.getById(id)
}