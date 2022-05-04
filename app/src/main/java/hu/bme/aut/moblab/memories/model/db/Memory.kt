package hu.bme.aut.moblab.memories.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory")
data class Memory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="memoryId", index = true)
    var memoryId: String?,
    var title: String,
    var description: String,
)
