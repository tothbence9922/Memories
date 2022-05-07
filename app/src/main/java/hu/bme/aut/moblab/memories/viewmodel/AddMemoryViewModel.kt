package hu.bme.aut.moblab.memories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.AddMemoryUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitAddMemoryUseCase
import hu.bme.aut.moblab.memories.model.db.Memory
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddMemoryViewModel @Inject constructor(
    private val addMemoryUseCase: AddMemoryUseCase,
    private val retrofitAddMemoryUseCase: RetrofitAddMemoryUseCase,
) : ViewModel() {
    val title = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")

    fun addMemory() {
        val newMemory = Memory(
            UUID.randomUUID().toString(),
            title.value.orEmpty(),
            description.value.orEmpty(),
            "asd",
            ""
        )
        viewModelScope.launch {
            addMemoryUseCase.invoke(newMemory)
        }
    }

    fun retrofitAddMemory() {
        val newMemory = Memory(
            UUID.randomUUID().toString(),
            title.value.orEmpty(),
            description.value.orEmpty(),
            "asd",
            ""
        )
        viewModelScope.launch {
            retrofitAddMemoryUseCase.invoke(newMemory)
        }
    }

}