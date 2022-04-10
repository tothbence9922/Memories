package hu.bme.aut.moblab.memories.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hu.bme.aut.moblab.memories.database.AppDatabase
import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.repository.MemoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoriesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MemoryRepository
    val allMemories: LiveData<List<Memory>>

    init {
        val memoriesDao = AppDatabase.getInstance(application).memoryDao()
        repository = MemoryRepository(memoriesDao)
        allMemories = repository.getAllMemories()
    }

    fun insert(memory: Memory) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(memory)
    }

    fun delete(memory: Memory) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(memory)
    }

}