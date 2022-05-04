package hu.bme.aut.moblab.memories.view.memory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.network.MemoriesAPI
import kotlinx.android.synthetic.main.activity_memory_details.*
import kotlinx.android.synthetic.main.memory_row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MemoryDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_MEMORY = "KEY_MEMORY"
    }

    private lateinit var memoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_details)

        memoryName = intent.getStringExtra(KEY_MEMORY)!!
        tvMemory.text = memoryName
    }

    override fun onResume() {
        super.onResume()

        getMemoryData()
    }

    private fun getMemoryData() {
        val getByIdCall = prepareCall()

        getByIdCall.enqueue(object : Callback<MemoryDTO> {
            override fun onFailure(call: Call<MemoryDTO>, t: Throwable) {
                tvMemoryName.text = t.message
            }

            override fun onResponse(call: Call<MemoryDTO>, response: Response<MemoryDTO>) {
                val weatherData = response.body()
                processResponse(weatherData)
            }
        })
    }

    private fun processResponse(
        memoryData: MemoryDTO?
    ) {
        tvMain.text = memoryData?.title.toString()
        tvDescription.text = memoryData?.description.toString()

        val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
        val date = Date((memoryData?.date?.toLong())!! * 1000)
        val time = sdf.format(date)
        tvDate.text = time
    }

    private fun prepareCall(): Call<MemoryDTO> {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://memories-3d5e8-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(MemoriesAPI::class.java)

        val weatherCall =
            weatherApi.getMemoryById(
                memoryName
            )
        return weatherCall
    }

}