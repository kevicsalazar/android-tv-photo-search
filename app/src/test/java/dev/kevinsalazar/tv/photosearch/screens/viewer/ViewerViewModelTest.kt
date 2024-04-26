package dev.kevinsalazar.tv.photosearch.screens.viewer

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.kevinsalazar.tv.photosearch.utils.CoroutinesTestExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class ViewerViewModelTest {

    private val savedStateHandle = mockk<SavedStateHandle>()

    @Test
    fun `GIVEN ViewModel WHEN event OnLoadPhoto comes THEN load photo with url`() =
        runTest {

            every {
                savedStateHandle.get<String>("url")
            } returns "https://images.unsplash.com/photo-1710555331394-6f096f01f213"

            val viewModel = ViewerViewModel(
                savedStateHandle = savedStateHandle,
            )

            viewModel.state.test {

                viewModel.event(ViewerContract.Event.OnLoadPhoto)

                skipItems(1)

                with(awaitItem()) {
                    url shouldBe "https://images.unsplash.com/photo-1710555331394-6f096f01f213"
                }
            }
        }
}
