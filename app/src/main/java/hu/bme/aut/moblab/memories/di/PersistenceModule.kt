package hu.bme.aut.moblab.memories.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.moblab.memories.database.AppDatabase
import hu.bme.aut.moblab.memories.database.MemoryDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "memories"
    ).build()

    @Provides
    @Singleton
    fun provideMemoryDAO(appDatabase: AppDatabase): MemoryDAO {
        return appDatabase.memoryDao()
    }
}