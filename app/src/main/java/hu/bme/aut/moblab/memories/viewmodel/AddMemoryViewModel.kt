package hu.bme.aut.moblab.memories.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.moblab.memories.bl.offline.AddMemoryUseCase
import hu.bme.aut.moblab.memories.bl.online.RetrofitAddMemoryUseCase
import hu.bme.aut.moblab.memories.model.db.Memory
import kotlinx.coroutines.launch
import java.io.File
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
            "file:" + imageUri.value.orEmpty(),
            getDate(Date().time)
        )
        viewModelScope.launch {
            addMemoryUseCase.invoke(newMemory)
        }
    }

    fun retrofitAddMemory() {

        val storageRef = Firebase.storage.reference
        val file = Uri.fromFile(File(imageUri.value.orEmpty()))
        val imageRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = imageRef.putFile(file)
        var downloadUrl = ""
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
                downloadUrl = downloadUri.toString()
                val newMemory = Memory(
                    UUID.randomUUID().toString(),
                    title.value.orEmpty(),
                    description.value.orEmpty(),
                    downloadUrl,
                    getDate(Date().time)
                )
                viewModelScope.launch {
                    retrofitAddMemoryUseCase.invoke(newMemory)
                }
            } else {
                Log.w("Upload failure", "Upload unsuccessful.")
            }
        }

    }

}