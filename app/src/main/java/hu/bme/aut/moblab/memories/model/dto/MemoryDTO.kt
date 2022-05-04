package hu.bme.aut.moblab.memories.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemoryDTO(
    val id: String,
    val date: Double,
    val description: String,
    val imageUrls: String,
    val title: String
)