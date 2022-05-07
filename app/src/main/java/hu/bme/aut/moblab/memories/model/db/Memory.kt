package hu.bme.aut.moblab.memories.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO

@Entity(tableName = "memory")
data class Memory(
    @PrimaryKey
    @ColumnInfo(name = "memoryId", index = true)
    var memoryId: String,
    var title: String?,
    var description: String?,
    var imageUrls: String?,
    val created: String?
)

fun Memory.toDto() = MemoryDTO(
    memoryId = memoryId,
    title = title!!,
    description = description!!,
    imageUrls = imageUrls!!,
    created = created!!,
    _id = null
)