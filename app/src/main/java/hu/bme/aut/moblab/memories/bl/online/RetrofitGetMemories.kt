package hu.bme.aut.moblab.memories.bl.online


import hu.bme.aut.moblab.memories.repository.MemoryRetrofitRepository
import javax.inject.Inject

class RetrofitGetMemories @Inject constructor(
    private val memoryRepository: MemoryRetrofitRepository
) {

    suspend operator fun invoke() = memoryRepository.getAllMemories()
}