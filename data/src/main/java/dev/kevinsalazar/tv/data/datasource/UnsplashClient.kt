package dev.kevinsalazar.tv.data.datasource

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object UnsplashClient {

    private const val BASE_URL = "https://api.unsplash.com/"
    private const val ACCESS_KEY = "cY3Gq40mQ4qJ8N78ca1nmtat81RQWlX74ShdThskJIA"

    private val client
        get() = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Client-ID $ACCESS_KEY")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    private val json
        get() = Json {
            ignoreUnknownKeys = true
        }

    private val retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()

    val api: UnsplashApi get() = retrofit.create(UnsplashApi::class.java)

}
