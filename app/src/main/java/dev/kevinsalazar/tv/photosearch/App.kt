package dev.kevinsalazar.tv.photosearch

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dev.kevinsalazar.tv.data.di.dataModule
import dev.kevinsalazar.tv.domain.di.domainModule
import dev.kevinsalazar.tv.photosearch.di.AppModule
import dev.kevinsalazar.tv.photosearch.di.AppModuleImpl

class App : Application(), ImageLoaderFactory {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(
            appContext = this,
            dataModule = dataModule,
            domainModule = domainModule
        )
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}
