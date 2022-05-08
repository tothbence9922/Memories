package hu.bme.aut.moblab.memories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.AddMemoryUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitAddMemoryUseCase
import hu.bme.aut.moblab.memories.model.db.Memory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddMemoryViewModel @Inject constructor(
    private val addMemoryUseCase: AddMemoryUseCase,
    private val retrofitAddMemoryUseCase: RetrofitAddMemoryUseCase,
) : ViewModel() {
    val title = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")
    val imageUri = MutableLiveData<String>(null)

    private fun getDate(milliSeconds: Long): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun addMemory() {
        val newMemory = Memory(
            UUID.randomUUID().toString(),
            title.value.orEmpty(),
            description.value.orEmpty(),
            imageUri.value.orEmpty(),
            getDate(Date().time)
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
            imageUri.value.orEmpty(),
            getDate(Date().time)
        )
        viewModelScope.launch {
            retrofitAddMemoryUseCase.invoke(newMemory)
        }
    }

}