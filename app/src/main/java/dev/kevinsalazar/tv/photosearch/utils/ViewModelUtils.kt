package dev.kevinsalazar.tv.photosearch.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kevinsalazar.tv.photosearch.App

fun <VM : ViewModel> viewModelFactory(initializer: (CreationExtras) -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return  initializer(extras) as T
        }
    }
}

@Composable
inline fun <reified VM : ViewModel> getViewModel(): VM {
    return viewModel(factory = App.appModule.getViewModelFactory(VM::class.java))
}
