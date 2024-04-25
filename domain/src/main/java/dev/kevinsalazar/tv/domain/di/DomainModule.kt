package dev.kevinsalazar.tv.domain.di

import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import dev.kevinsalazar.tv.domain.usecases.GetPhotosUseCase
import dev.kevinsalazar.tv.domain.usecases.SearchPhotosUseCase

val domainModule: DomainModule get() = DomainModuleImpl()

interface DomainModule {
    fun getRandomPhotosUseCase(
        repository: PhotoRepository
    ): GetPhotosUseCase

    fun searchPhotosUseCase(
        repository: PhotoRepository
    ): SearchPhotosUseCase
}

internal class DomainModuleImpl : DomainModule {
    override fun getRandomPhotosUseCase(repository: PhotoRepository): GetPhotosUseCase {
        return GetPhotosUseCase(repository)
    }

    override fun searchPhotosUseCase(repository: PhotoRepository): SearchPhotosUseCase {
        return SearchPhotosUseCase(repository)
    }
}
