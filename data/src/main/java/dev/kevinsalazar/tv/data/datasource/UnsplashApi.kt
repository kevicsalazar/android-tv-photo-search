package dev.kevinsalazar.tv.data.datasource

import dev.kevinsalazar.tv.data.datasource.dto.PhotoDTO
import dev.kevinsalazar.tv.data.datasource.dto.ResultsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("count") count: Int
    ): List<PhotoDTO>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
    ): ResultsDTO<PhotoDTO>
}
