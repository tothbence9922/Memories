package hu.bme.aut.moblab.memories.util
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.moblab.memories.model.db.Memory
import org.json.JSONObject

class MoshiConverter {
    private val _moshiBuilder = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromMemory(memory: Memory): String {
        return JSONObject().apply {
            put("memoryId", memory.memoryId)
            put("title", memory.title)
            put("description", memory.description)
            put("imageUrls", memory.imageUrls)
            put("created", memory.created)
        }.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toMemory(memory: String): Memory {
        val json = JSONObject(memory)
        return Memory(
            json.getString("memoryId"),
            json.getString("title"),
            json.getString("description"),
            json.getString("imageUrls"),
            json.getString("created")
        )
    }
}

inline fun <reified T> Moshi.fromJson(json: String) = this.adapter(T::class.java).fromJson(json)

inline fun <reified T> Moshi.toJson(value: T): String = this.adapter(T::class.java).toJson(value)