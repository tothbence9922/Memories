package hu.bme.aut.moblab.memories.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.UpdateMemory
import hu.bme.aut.moblab.memories.bl.online.RetrofitUpdateMemory
import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateMemory: UpdateMemory,
    private val retrofitUpdateMemory: RetrofitUpdateMemory,
) : ViewModel() {

    val id = savedStateHandle.get<String>("id")!!
    val isOnline = savedStateHandle.get<Boolean>("isOnline")!!
    private val originalTitle = savedStateHandle.get<String>("title")!!
    private val originalDescription = savedStateHandle.get<String>("description")!!
    private val originalCreated = savedStateHandle.get<String>("created")!!
    private val originalImageUrls = savedStateHandle.get<String>("imageUrls")!!
    private val originalMemoryId = savedStateHandle.get<String>("memoryId")!!

    val title = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")
    val imageUri = MutableLiveData<String>(null)

    init {
        title.value = originalTitle
        description.value = originalDescription
        imageUri.value = originalImageUrls
    }

    fun putMemory() {
        var urlString = if (imageUri.value!!.contains("file:")){
            imageUri.value
        } else {
            "file:" + imageUri.value
        }

        val newMemory = Memory(
            originalMemoryId,
            title.value.orEmpty(),
            description.value.orEmpty(),
            urlString,
            originalCreated
        )
        viewModelScope.launch {
            updateMemory.invoke(newMemory)
        }
    }

    fun retrofitPutMemory() {
        val storageRef = Firebase.storage.reference
        val file = File(imageUri.value.orEmpty())
        if (file.exists()) {
            val uri = Uri.fromFile(file)
            val imageRef = storageRef.child("images/${uri.lastPathSegment}")
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnFailureListener {
            }.addOnSuccessListener { taskSnapshot ->
                Log.v("Upload metadata:", taskSnapshot.metadata.toString())
            }
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val downloadUrl = downloadUri.toString()
                    val newMemory = MemoryDTO(
                        id,
                        originalMemoryId,
                        title.value.orEmpty(),
                        description.value.orEmpty(),
                        downloadUrl,
                        originalCreated
                    )
                    viewModelScope.launch {
                        retrofitUpdateMemory.invoke(newMemory)
                    }
                } else {
                    Log.w("Upload failure", "Upload unsuccessful.")
                }
            }
        } else {
            val newMemory = MemoryDTO(
                id,
                originalMemoryId,
                title.value.orEmpty(),
                description.value.orEmpty(),
                originalImageUrls,
                originalCreated
            )
            viewModelScope.launch {
                retrofitUpdateMemory.invoke(newMemory)
            }
        }

    }

}