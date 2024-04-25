package dev.kevinsalazar.tv.photosearch.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.kevinsalazar.tv.data.di.DataModule
import dev.kevinsalazar.tv.domain.di.DomainModule
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryViewModel
import dev.kevinsalazar.tv.photosearch.screens.gallery.mappers.GalleryMapper
import dev.kevinsalazar.tv.photosearch.screens.viewer.ViewerViewModel
import dev.kevinsalazar.tv.photosearch.utils.DefaultTextProvider
import dev.kevinsalazar.tv.photosearch.utils.NoViewModelProvidedException
import dev.kevinsalazar.tv.photosearch.utils.TextProvider
import dev.kevinsalazar.tv.photosearch.utils.viewModelFactory

interface AppModule {
    val textProvider: TextProvider
    val galleryMapper: GalleryMapper
    fun <VM : ViewModel> getViewModelFactory(clazz: Class<VM>): ViewModelProvider.Factory
}

class AppModuleImpl(
    private val appContext: Context,
    private val dataModule: DataModule,
    private val domainModule: DomainModule,
) : AppModule {

    override val textProvider: TextProvider
        get() = DefaultTextProvider(appContext)

    override val galleryMapper: GalleryMapper
        get() = GalleryMapper()

    override fun <VM : ViewModel> getViewModelFactory(clazz: Class<VM>): ViewModelProvider.Factory {
        return viewModelFactory {
            when (clazz) {
                GalleryViewModel::class.java -> getHomeViewModelInitializer()
                ViewerViewModel::class.java -> getViewerViewModelInitializer(it)
                else -> throw NoViewModelProvidedException()
            }
        }
    }

    private fun getHomeViewModelInitializer(): GalleryViewModel {
        return GalleryViewModel(
            getPhotosUseCase = domainModule.getRandomPhotosUseCase(
                repository = dataModule.repository
            ),
            searchPhotosUseCase = domainModule.searchPhotosUseCase(
                repository = dataModule.repository
            ),
            galleryMapper = galleryMapper,
            textProvider = textProvider
        )
    }

    private fun getViewerViewModelInitializer(extras: CreationExtras): ViewerViewModel {
        return ViewerViewModel(
            savedStateHandle = extras.createSavedStateHandle()
        )
    }
}
