package hu.bme.aut.moblab.memories.tests.unit.db

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
class Tests {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Inject
    lateinit var database: AppDatabase

    @ExperimentalCoroutinesApi
    @Test
    fun insertMemoryTest() = runTest {
        // Arrange
        val dao = database.memoryDao()

        // Act
        dao.insert(
            Memory(
                memoryId = "offline_id",
                title = "Title",
                description = "Description",
                imageUrls = "",
                created = "2022-05-06 14:22:32"
            )
        )

        // Assert
        assertEquals(1, dao.getAll().size)
    }

    @After
    fun close() {
        database.close()
    }
}