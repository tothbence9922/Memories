package hu.bme.aut.moblab.memories.bl.online

import hu.bme.aut.moblab.memories.repository.MemoryRepository
import hu.bme.aut.moblab.memories.repository.MemoryRetrofitRepository
import javax.inject.Inject

class RetrofitGetMemoryByIdUseCase @Inject constructor(
    private val memoryRepository: MemoryRetrofitRepository
) {

    suspend operator fun invoke(id: String) = memoryRepository.getById(id)
}