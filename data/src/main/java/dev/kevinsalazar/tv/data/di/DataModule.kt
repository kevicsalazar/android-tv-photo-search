package dev.kevinsalazar.tv.data.di

import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.datasource.UnsplashClient
import dev.kevinsalazar.tv.data.repository.DefaultPhotoRepository
import dev.kevinsalazar.tv.domain.repository.PhotoRepository

val dataModule: DataModule get() = DataModuleImpl()

interface DataModule {
    val api: UnsplashApi
    val repository: PhotoRepository
}

internal class DataModuleImpl : DataModule {
    override val api: UnsplashApi by lazy {
        UnsplashClient.api
    }
    override val repository: PhotoRepository by lazy {
        DefaultPhotoRepository(api)
    }
}
