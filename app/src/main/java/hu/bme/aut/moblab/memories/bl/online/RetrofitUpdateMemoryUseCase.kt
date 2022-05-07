package hu.bme.aut.moblab.memories.bl.online

import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.model.db.toDto
import hu.bme.aut.moblab.memories.repository.MemoryRepository
import hu.bme.aut.moblab.memories.repository.MemoryRetrofitRepository
import javax.inject.Inject

class RetrofitUpdateMemoryUseCase @Inject constructor(
    private val memoryRepository: MemoryRetrofitRepository
) {

    suspend operator fun invoke(memory: Memory) = memoryRepository.update(memory.toDto())
}