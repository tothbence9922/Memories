package hu.bme.aut.moblab.memories.di.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hu.bme.aut.moblab.memories.database.AppDatabase
import hu.bme.aut.moblab.memories.database.MemoryDAO
import hu.bme.aut.moblab.memories.di.DatabaseModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseMockModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMemoryDAO(appDatabase: AppDatabase): MemoryDAO {
        return appDatabase.memoryDao()
    }

}