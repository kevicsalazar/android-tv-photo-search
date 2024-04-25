package dev.kevinsalazar.tv.photosearch.utils

import android.content.Context
import dev.kevinsalazar.tv.photosearch.R

interface TextProvider {
    val trendingNow: String
    val noResults: String
    fun getSearchResultsFor(query: String): String
    fun getNoResultsFor(query: String): String
}

class DefaultTextProvider(
    private val context: Context
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
}
