package hu.bme.aut.moblab.memories.model.dto

import com.squareup.moshi.JsonClass
import hu.bme.aut.moblab.memories.model.db.Memory

@JsonClass(generateAdapter = true)
data class MemoryDTO(
    val _id : String?,
    val memoryId: String,
    val title: String,
    val description: String,
    val imageUrls: String,
    val created: String
)

fun MemoryDTO.toMemory() = Memory(
    memoryId = memoryId,
    title = title,
    description = description,
    imageUrls = imageUrls,
    created = created
)

