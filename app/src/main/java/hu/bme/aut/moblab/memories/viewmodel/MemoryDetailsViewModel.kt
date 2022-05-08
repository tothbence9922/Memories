package hu.bme.aut.moblab.memories.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.GetMemoryByIdUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitGetMemoryByIdUseCase
import hu.bme.aut.moblab.memories.model.db.toDto
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMemoryByIdUseCase: GetMemoryByIdUseCase,
    private val retrofitGetMemoryByIdUseCase: RetrofitGetMemoryByIdUseCase,
) : ViewModel() {

    val id = savedStateHandle.get<String>("id")!!
    val isOnline = savedStateHandle.get<Boolean>("isOnline")!!

    val memory = MutableLiveData<MemoryDTO>()

    init {
        getMemoryById()
    }

    private fun getMemoryById() {
        viewModelScope.launch {
            if (!isOnline) {
                memory.value = getMemoryByIdUseCase(id)!!.toDto()
            } else {
                memory.value = retrofitGetMemoryByIdUseCase(id)!!
            }
            Log.v("MemoryDetailsViewModel", memory.value.toString())
        }
    }

}