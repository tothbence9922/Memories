package hu.bme.aut.moblab.memories.bl.online

import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.repository.MemoryRetrofitRepository
import javax.inject.Inject

class RetrofitUpdateMemoryUseCase @Inject constructor(
    private val memoryRepository: MemoryRetrofitRepository
) {

    suspend operator fun invoke(memory: MemoryDTO) = memoryRepository.update(memory)
}