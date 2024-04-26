package dev.kevinsalazar.tv.data.repository

import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.utils.loadFileJson
import dev.kevinsalazar.tv.domain.values.Result
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.coEvery
import io.mockk.mockk


class PhotoRepositoryTest : StringSpec() {

    private val unsplashApi = mockk<UnsplashApi>()

    init {
        "GIVEN PhotoRepository WHEN getRandomPhotos() THEN return photos as Result" {

            coEvery {
                unsplashApi.getRandomPhotos(30)
            } returns loadFileJson("/random_photos.json")

            val repository = DefaultPhotoRepository(
                api = unsplashApi
            )

            val result = repository.getRandomPhotos()

            result as Result.Success
            result.data shouldHaveSize 2
        }
        "GIVEN PhotoRepository WHEN searchPhotos() THEN return photos results as Result" {

            coEvery {
                unsplashApi.searchPhotos(1, "flags", 30)
            } returns loadFileJson("/search_results.json")

            val repository = DefaultPhotoRepository(
                api = unsplashApi
            )

            val result = repository.searchPhotos(1, "flags")

            result as Result.Success
            result.data shouldHaveSize 2
        }
    }
}
