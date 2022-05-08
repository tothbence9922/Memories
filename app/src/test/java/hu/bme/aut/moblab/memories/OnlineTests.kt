package hu.bme.aut.moblab.memories

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO
import hu.bme.aut.moblab.memories.network.MemoriesAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class NetworkUnitTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Inject
    lateinit var api: MemoriesAPI

    @Inject
    lateinit var mockWebServer: MockWebServer

    private val sampleMemeoryDTO1 = MemoryDTO(
        _id = "_id_1",
        memoryId = "memory_id_1",
        title = "sample_title_1",
        description = "sample_description_1",
        imageUrls = "/storage/emulated/0/DCIM/Camera/.jpg",
        created = "2022-05-08 03:50:52"
    )

    private val sampleMemeoryDTO2 = MemoryDTO(
        _id = null,
        memoryId = "1c109db5-7284-4ed6-a65c-1f10118bf4f8",
        title = "sample_title_2",
        description = "sample_description_2",
        imageUrls = "/storage/emulated/0/DCIM/Camera/.jpg",
        created = "2022-05-08 03:50:52"
    )

    @ApplicationContext
    @Inject
    lateinit var context: Context

    @ExperimentalCoroutinesApi
    @Test
    fun addMemory() = runTest {
        // Arrange
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<MemoryDTO> =
            moshi.adapter(MemoryDTO::class.java)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(jsonAdapter.toJson(sampleMemeoryDTO1))
        )

        // Act
        val actualDto = api.createMemory(sampleMemeoryDTO1)

        // Assert
        assertEquals(sampleMemeoryDTO1.toString(), actualDto.toString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteMemory() = runTest {
        // Arrange
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
        )

        // Act
        api.deleteMemory("_id_1")

        val request = mockWebServer.takeRequest()

        // Assert
        assertEquals("DELETE", request.method)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMemories() = runTest {
        // Arrange
        val moshi = Moshi.Builder().build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            MemoryDTO::class.java
        )
        val adapter: JsonAdapter<List<MemoryDTO>> = moshi.adapter<List<MemoryDTO>>(listMyData)

        val body: ArrayList<MemoryDTO> = arrayListOf(sampleMemeoryDTO1, sampleMemeoryDTO2)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(adapter.toJson(body))
        )

        // Act
        val actualDto = api.getMemories()

        // Assert
        assertEquals(body.toString(), actualDto.toString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMemoryById() = runTest {
        // Arrange
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<MemoryDTO> =
            moshi.adapter(MemoryDTO::class.java)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(jsonAdapter.toJson(sampleMemeoryDTO1))
        )

        // Act
        val actualDto = api.getMemoryById(sampleMemeoryDTO1._id!!)

        // Assert
        assertEquals(sampleMemeoryDTO1.toString(), actualDto.toString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMemoryByNullId() = runTest {
        // Arrange
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<MemoryDTO> =
            moshi.adapter(MemoryDTO::class.java)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(jsonAdapter.toJson(sampleMemeoryDTO1))
        )

        // Act && Assert
        try {
            api.getMemoryById(sampleMemeoryDTO2._id!!)
        } catch (e: NullPointerException) {
            assertEquals(e::class, java.lang.NullPointerException::class)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateMemory() = runTest {
        // Arrange
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
        )
        var updatedMemoryDTO = sampleMemeoryDTO1
        updatedMemoryDTO.title = "UPDATED"
        // Act
        api.updateMemory("_id_1", updatedMemoryDTO)

        val request = mockWebServer.takeRequest()

        // Assert
        assertEquals("PUT", request.method)
    }

    @After
    fun close() {
        mockWebServer.shutdown()
    }

}