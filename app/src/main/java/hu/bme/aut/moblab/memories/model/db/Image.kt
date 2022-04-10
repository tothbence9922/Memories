package hu.bme.aut.moblab.memories.model.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "image", foreignKeys = [ForeignKey(entity = Memory::class, parentColumns = arrayOf("memoryId"), childColumns = arrayOf("memoryId"), onDelete = ForeignKey.CASCADE)])
data class Image (
    @PrimaryKey(autoGenerate = true) var imageId: Long?,
    var memoryId: Long,
    var url : String,
)
