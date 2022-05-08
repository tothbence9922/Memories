package hu.bme.aut.moblab.memories.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.DeleteMemoryUseCase
import hu.bme.aut.moblab.memories.bl.offline.GetMemoriesUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitDeleteMemoryUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitGetMemoriesUseCase
import hu.bme.aut.moblab.memories.model.db.toDto
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.model.dto.toMemory
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoriesViewModel @Inject constructor(
    private val getMemoriesUseCase: GetMemoriesUseCase,
    private val deleteMemoryUseCase: DeleteMemoryUseCase,
    private val retrofitGetMemoriesUseCase: RetrofitGetMemoriesUseCase,
    private val retrofitDeleteMemoryUseCase: RetrofitDeleteMemoryUseCase
) : ViewModel() {

    val memories = MutableLiveData(emptyList<MemoryDTO>())
    val isOnline = MutableLiveData(false)
    var state = MutableLiveData("Local memories")

    init {
        getAllMemories()
    }

    fun toggleOnline() {
        viewModelScope.launch {
            isOnline.value = !isOnline.value!!
            if (isOnline.value!!){
                state.value = "Online memories"
            } else {
                state.value = "Local memories"
            }
            getAllMemories()
        }
        Log.v("TAG", "TOGGLED TO: ${isOnline.value}")
    }

    fun getAllMemories() {
        viewModelScope.launch {
            if (isOnline.value == false) {
                memories.value = getMemoriesUseCase.invoke().map { it.toDto() }
            } else {
                memories.value = retrofitGetMemoriesUseCase.invoke()
            }
        }
        Log.v("TAG", "getAllMemories called")

    }

    fun delete(memoryDTO: MemoryDTO) {

        viewModelScope.launch {
            if (isOnline.value == false) {
                deleteMemoryUseCase.invoke(memoryDTO.toMemory())
            } else {
                retrofitDeleteMemoryUseCase.invoke(memoryDTO)
            }
            getAllMemories()
        }
    }
}
