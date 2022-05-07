package hu.bme.aut.moblab.memories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.GetMemoryByIdUseCase
import hu.bme.aut.moblab.memories.model.db.Memory
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMemoryByIdUseCase: GetMemoryByIdUseCase,
) : ViewModel() {

    private val id = savedStateHandle.get<String>("id")!!

    val memory = MutableLiveData<Memory>()

    init {
        getMemory()
    }

    private fun getMemory() {
        viewModelScope.launch {
            getMemoryByIdUseCase(id).let {
                memory.value = it
            }
        }
    }

}