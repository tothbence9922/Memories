package hu.bme.aut.moblab.memories.network

import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import retrofit2.Call
import retrofit2.http.*

interface MemoriesAPI {

    @POST("/memory")
    fun createMemory(@Body memoryDto: MemoryDTO): Call<MemoryResult>

    @GET("/memory")
    fun getMemories(): Call<MemoryResult>

    @GET("/memory")
    fun getMemoryById(@Query("id") memoryId: String): Call<MemoryResult>

    @PUT("/memory")
    fun updateMemory(@Body memoryDto: MemoryDTO): Call<MemoryResult>

}