package hu.bme.aut.moblab.memories.di.repository

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hu.bme.aut.moblab.memories.database.AppDatabase
import hu.bme.aut.moblab.memories.network.MemoriesAPI
import hu.bme.aut.moblab.memories.repository.MemoryRepository
import hu.bme.aut.moblab.memories.repository.MemoryRetrofitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MemoryRepository::class, MemoryRetrofitRepository::class]
)
object RepositoryMockModule {
    @Inject
    lateinit var database: AppDatabase
    @Inject
    lateinit var memoriesAPI: MemoriesAPI

    @Provides
    @Singleton
    fun provideMemoryRetrofitRepository(@ApplicationContext context: Context): MemoryRetrofitRepository {
        return MemoryRetrofitRepository(memoriesAPI)
    }

    @Provides
    @Singleton
    fun provideMemoryRepository(appDatabase: AppDatabase): MemoryRepository {
        return MemoryRepository(database.memoryDao())
    }

}