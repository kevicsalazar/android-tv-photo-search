package dev.kevinsalazar.tv.data.datasource

import dev.kevinsalazar.tv.data.datasource.remote.dto.PhotoDto
import dev.kevinsalazar.tv.data.datasource.remote.dto.ResultsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("count") count: Int
    ): List<PhotoDto>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ResultsDto<PhotoDto>
}
