package hu.bme.aut.moblab.memories.network

import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import retrofit2.http.*

interface MemoriesAPI {

    @POST("memories")
    suspend fun createMemory(@Body memoryDto: MemoryDTO): MemoryDTO

    @GET("memories")
    suspend fun getMemories(): List<MemoryDTO>

    @GET("memories/{id}")
    suspend fun getMemoryById(@Path("id") id: String): MemoryDTO

    @PUT("memories/{id}")
    suspend fun updateMemory(@Path("id") id: String, @Body memoryDto: MemoryDTO)

    @DELETE("memories/{id}")
    suspend fun deleteMemory(@Path("id") id: String)

}