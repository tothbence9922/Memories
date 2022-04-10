package hu.bme.aut.moblab.memories.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemoryDTO (
    val memoryId: Int?,
    val title: String?,
    val description: String?,
    val imageUrls: ArrayList<ImageDTO>?
)