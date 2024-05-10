package dev.kevinsalazar.tv.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.datasource.UnsplashClient
import dev.kevinsalazar.tv.data.datasource.local.PhotoDatabase
import dev.kevinsalazar.tv.data.repository.DefaultPhotoRepository
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinds {

    @Binds
    fun bindPhotoRepository(repository: DefaultPhotoRepository): PhotoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApi(): UnsplashApi {
        return UnsplashClient.api
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photos.db"
        ).build()
    }
}
