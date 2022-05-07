package hu.bme.aut.moblab.memories.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.util.MoshiConverter

@TypeConverters(MoshiConverter::class)
@Database(entities = [Memory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memoryDao(): MemoryDAO

}