package hu.bme.aut.moblab.memories.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDTO (
    val imageId: Int?,
    val memoryId: Int?,
    val url: String?
)