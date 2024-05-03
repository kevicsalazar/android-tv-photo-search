package dev.kevinsalazar.tv.photosearch.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.kevinsalazar.tv.domain.errors.DataError
import dev.kevinsalazar.tv.domain.errors.Error
import dev.kevinsalazar.tv.photosearch.R
import javax.inject.Inject

interface TextProvider {
    val trendingNow: String
    val noResults: String
    fun getSearchResultsFor(query: String): String
    fun getNoResultsFor(query: String): String
    fun getErrorMessage(error: Error): String
}

class DefaultTextProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : TextProvider {

    private val resources by lazy { context.resources }

    override val trendingNow: String
        get() = resources.getString(R.string.trending_now)

    override val noResults: String
        get() = resources.getString(R.string.no_results)

    override fun getSearchResultsFor(query: String): String {
        return resources.getString(R.string.search_results_for, query)
    }

    override fun getNoResultsFor(query: String): String {
        return resources.getString(R.string.no_results_for, query)
    }

    override fun getErrorMessage(error: Error): String {
        return when (error) {
            DataError.Network.NO_INTERNET -> resources.getString(R.string.no_internet_error)
            DataError.Network.UNAUTHORIZED -> resources.getString(R.string.access_denied_error)
            else -> resources.getString(R.string.generic_error)
        }
    }
}
