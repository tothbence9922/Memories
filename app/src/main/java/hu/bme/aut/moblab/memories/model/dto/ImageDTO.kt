package hu.bme.aut.moblab.memories.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDTO (
    val imageId: Long?,
    val memoryId: Long?,
    val url: String?
)