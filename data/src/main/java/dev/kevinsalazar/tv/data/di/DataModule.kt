package dev.kevinsalazar.tv.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.datasource.UnsplashClient
import dev.kevinsalazar.tv.data.repository.DefaultPhotoRepository
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindingDataModule {

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
}
