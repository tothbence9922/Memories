package hu.bme.aut.moblab.memories.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.DeleteMemory
import hu.bme.aut.moblab.memories.bl.offline.GetMemories
import hu.bme.aut.moblab.memories.bl.online.RetrofitDeleteMemory
import hu.bme.aut.moblab.memories.bl.online.RetrofitGetMemories
import hu.bme.aut.moblab.memories.model.db.toDto
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.model.dto.toMemory
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoriesViewModel @Inject constructor(
    private val getMemories: GetMemories,
    private val deleteMemory: DeleteMemory,
    private val retrofitGetMemories: RetrofitGetMemories,
    private val retrofitDeleteMemory: RetrofitDeleteMemory
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
                memories.value = getMemories.invoke().map { it.toDto() }
            } else {
                memories.value = retrofitGetMemories.invoke()
            }
        }
        Log.v("TAG", "getAllMemories called")

    }

    fun delete(memoryDTO: MemoryDTO) {

        viewModelScope.launch {
            if (isOnline.value == false) {
                deleteMemory.invoke(memoryDTO.toMemory())
            } else {
                retrofitDeleteMemory.invoke(memoryDTO)
            }
            getAllMemories()
        }
    }
}
