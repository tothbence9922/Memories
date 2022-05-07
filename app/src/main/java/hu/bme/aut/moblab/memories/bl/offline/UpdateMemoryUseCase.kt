package hu.bme.aut.moblab.memories.bl.offline

import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.repository.MemoryRepository
import javax.inject.Inject

class UpdateMemoryUseCase @Inject constructor(
    private val memoryRepository: MemoryRepository
) {

    suspend operator fun invoke(memory: Memory) = memoryRepository.update(memory)
}