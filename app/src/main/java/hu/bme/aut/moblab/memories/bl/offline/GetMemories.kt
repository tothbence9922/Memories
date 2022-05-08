package hu.bme.aut.moblab.memories.bl.offline

import hu.bme.aut.moblab.memories.repository.MemoryRepository
import javax.inject.Inject

class GetMemories @Inject constructor(
    private val memoryRepository: MemoryRepository
) {

    suspend operator fun invoke() = memoryRepository.getAllMemories()
}