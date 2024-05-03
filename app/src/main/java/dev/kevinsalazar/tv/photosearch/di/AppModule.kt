package dev.kevinsalazar.tv.photosearch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kevinsalazar.tv.photosearch.utils.DefaultTextProvider
import dev.kevinsalazar.tv.photosearch.utils.TextProvider

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun bindTextProvider(textProvider: DefaultTextProvider): TextProvider
}
