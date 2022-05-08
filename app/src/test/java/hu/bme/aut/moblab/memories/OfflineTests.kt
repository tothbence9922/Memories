package hu.bme.aut.moblab.memories

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import hu.bme.aut.moblab.memories.database.AppDatabase
import hu.bme.aut.moblab.memories.model.db.Memory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class OfflineTests {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val sampleMemory1 = Memory(
        memoryId = "offline_id_1",
        title = "Title_1",
        description = "Description_1",
        imageUrls = "",
        created = "2022-05-06 14:22:32"
    )
    private val sampleMemory2 = Memory(
        memoryId = "offline_id_2",
        title = "Title_2",
        description = "Description_2",
        imageUrls = "",
        created = "2022-05-06 14:22:32"
    )
    private val sampleMemory3 = Memory(
        memoryId = "offline_id_3",
        title = "Title_3",
        description = "Description_3",
        imageUrls = "",
        created = "2022-05-06 14:22:32"
    )



    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Inject
    lateinit var database: AppDatabase

    @ExperimentalCoroutinesApi
    @Test
    fun addMemory() = runTest {
        // Arrange
        val dao = database.memoryDao()

        // Act
        dao.insert(sampleMemory1)

        // Assert
        assertEquals(1, dao.getAll().size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteMemory() = runTest {
        // Arrange
        val dao = database.memoryDao()
        dao.insert(sampleMemory1)

        // Act
        dao.delete(sampleMemory1)

        // Assert
        assertEquals(0, dao.getAll().size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMemories() = runTest {
        // Arrange
        val dao = database.memoryDao()

        // Act
        dao.insert(sampleMemory1)
        dao.insert(sampleMemory2)
        dao.insert(sampleMemory3)

        // Assert
        assertEquals(3, dao.getAll().size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMemoryById() = runTest {
        // Arrange
        val dao = database.memoryDao()
        dao.insert(sampleMemory1)

        // Act
        val foundMemory = dao.getById("offline_id_1")

        // Assert
        assertEquals(foundMemory.toString(), sampleMemory1.toString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateMemory() = runTest {
        // Arrange
        val dao = database.memoryDao()
        dao.insert(sampleMemory1)

        val editedMemory = sampleMemory1
        editedMemory.title = "Edited Title"

        // Act
        dao.update(editedMemory)

        val updatedFoundMemory = dao.getById(sampleMemory1.memoryId)

        // Assert
        assertEquals(updatedFoundMemory.title.toString(), "Edited Title")
    }

    @After
    fun close() {
        database.close()
    }
}