package hu.bme.aut.moblab.memories.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory")
data class Memory (
    @PrimaryKey(autoGenerate = true) var memoryId: Long?,
     var title : String,
     var description : String,
)
