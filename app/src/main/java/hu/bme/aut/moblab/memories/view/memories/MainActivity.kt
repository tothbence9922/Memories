package hu.bme.aut.moblab.memories.view.memories

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.moblab.memories.view.memories.adapter.MemoryAdapter
import hu.bme.aut.moblab.memories.viewmodel.MemoriesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var memoryAdapter: MemoryAdapter
    private val memoriesViewModel: MemoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
