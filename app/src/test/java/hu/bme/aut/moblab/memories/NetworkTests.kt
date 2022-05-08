package hu.bme.aut.moblab.memories

import android.content.Context
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
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.nio.charset.Charset
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

    @ApplicationContext
    @Inject
    lateinit var context: Context

    private fun readJsonFromAssets(context: Context, filePath: String): String? {
        return try {
            val source = context.assets.open(filePath).source().buffer()
            source.readByteString().string(Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertMemory() = runTest {
        // Arrange
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(readJsonFromAssets(context, "sampleMemory.json").orEmpty())
        )

        // Act
        val actualDto = api.getMemoryById("627721fb6f047803e8ae4ba9")

        // Assert
        val expectedDto = MemoryDTO(
            _id = "627721fb6f047803e8ae4ba9",
            memoryId = "1c109db5-7284-4ed6-a65c-1f10118bf4f8",
            title = "beep boop",
            description = "és dől a lé",
            imageUrls = "/storage/emulated/0/DCIM/Camera/20220502_171423.jpg",
            created = "2022-05-08 03:50:52"
        )
        assertEquals(expectedDto, actualDto)
    }

    @After
    fun close() {
        mockWebServer.shutdown()
    }

}