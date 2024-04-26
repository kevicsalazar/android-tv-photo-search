package dev.kevinsalazar.tv.photosearch.screens.gallery

import app.cash.turbine.test
import dev.kevinsalazar.tv.domain.usecases.GetPhotosUseCase
import dev.kevinsalazar.tv.domain.usecases.SearchPhotosUseCase
import dev.kevinsalazar.tv.domain.values.Result
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Event
import dev.kevinsalazar.tv.photosearch.screens.gallery.mappers.GalleryMapper
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import dev.kevinsalazar.tv.photosearch.utils.CoroutinesTestExtension
import dev.kevinsalazar.tv.photosearch.utils.TextProvider
import dev.kevinsalazar.tv.photosearch.utils.dummyPhotos
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class GalleryViewModelTest {

    private val getPhotosUseCase = mockk<GetPhotosUseCase>()
    private val searchPhotosUseCase = mockk<SearchPhotosUseCase>()
    private val textProvider = mockk<TextProvider> {
        every { trendingNow } returns "Trending Now"
        every { noResults } returns "No Results"
    }

    @Test
    fun `GIVEN the ViewModel waits -WHEN the event OnLoadPhotos comes THEN load photos with result`() =
        runTest {
            coEvery { getPhotosUseCase() } returns Result.Success(dummyPhotos)

            val viewModel = GalleryViewModel(
                getPhotosUseCase = getPhotosUseCase,
                searchPhotosUseCase = searchPhotosUseCase,
                galleryMapper = GalleryMapper(),
                textProvider = textProvider
            )

            viewModel.state.test {

                viewModel.event(Event.OnLoadPhotos)

                skipItems(1)

                with(awaitItem()) {
                    initialized shouldBe true
                    subtitle shouldBe "Trending Now"
                    photos shouldHaveSize 2
                    loading shouldBe false
                    searchMode shouldBe false
                }
            }
        }

    @Test
    fun `GIVEN the ViewModel waits WHEN the event OnSearchPhotos comes THEN search photos with result`() =
        runTest {
            coEvery { searchPhotosUseCase(1, "flags") } returns Result.Success(dummyPhotos)

            every { textProvider.getSearchResultsFor("flags") } returns "Search Results for \"flags\""
            every { textProvider.getNoResultsFor("flags") } returns "No Results for \"flags\""

            val viewModel = GalleryViewModel(
                getPhotosUseCase = getPhotosUseCase,
                searchPhotosUseCase = searchPhotosUseCase,
                galleryMapper = GalleryMapper(),
                textProvider = textProvider
            )

            viewModel.state.test {

                viewModel.event(Event.OnSearchPhotos("flags"))

                skipItems(1)

                with(awaitItem()) {
                    initialized shouldBe true
                    subtitle shouldBe "Search Results for \"flags\""
                    photos shouldHaveSize 2
                    loading shouldBe false
                    searchMode shouldBe true
                }
            }
        }

    @Test
    fun `GIVEN the ViewModel waits - WHEN the event OnPhotoClick comes THEN navigate to Viewer`() =
        runTest {

            val photoModel = mockk<PhotoModel> {
                every { raw } returns "https://imagen.jpg"
            }

            val viewModel = GalleryViewModel(
                getPhotosUseCase = getPhotosUseCase,
                searchPhotosUseCase = searchPhotosUseCase,
                galleryMapper = GalleryMapper(),
                textProvider = textProvider
            )

            viewModel.effect.test {

                viewModel.event(Event.OnPhotoClick(photoModel))

                awaitItem() shouldBe GalleryContract.Effect.Navigate(
                    route = "viewer?url=https://imagen.jpg"
                )
            }
        }

    @Test
    fun `GIVEN the ViewModel waits - WHEN the event OnSearchMode comes THEN enabled search mode`() =
        runTest {

            val viewModel = GalleryViewModel(
                getPhotosUseCase = getPhotosUseCase,
                searchPhotosUseCase = searchPhotosUseCase,
                galleryMapper = GalleryMapper(),
                textProvider = textProvider
            )

            viewModel.state.test {

                viewModel.event(Event.OnSearchMode)

                skipItems(1)

                with(awaitItem()) {
                    searchMode shouldBe true
                }
            }
        }

}
